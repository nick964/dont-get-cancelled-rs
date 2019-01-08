package com.nick.cancan.controller;

import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryResult;
import com.nick.cancan.repository.BadWordsRepository;
import com.nick.cancan.service.CancelledRequestService;
import com.nick.cancan.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;


@RestController
public class TwitterController {

  @Autowired
  CancelledRequestService cancelledRequestService;

  @Autowired
  BadWordsRepository badWordsRepository;

  @Autowired
  UserServiceImpl userService;

  @CrossOrigin
  @RequestMapping("/")
  public RequestToken getTweetsFromTwit(HttpServletRequest request) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();

    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:4200/authsuccess");


    request.getSession().setAttribute("requestToken", requestToken);
    request.getSession().setAttribute("twitter", twitter);

    return requestToken;
  }

  @CrossOrigin
  @RequestMapping("/badWords")
  public Iterable<BadWord> getTheTest() {
    return badWordsRepository.findAll();
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public AccessToken testSuccess(@RequestParam("oauth_token") String OAuthToken,
                            @RequestParam("oauth_verifier") String OAuthVerifier,
                                 HttpServletRequest request) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();
    AccessToken accessToken = twitter.getOAuthAccessToken(OAuthVerifier);
    twitter.setOAuthAccessToken(accessToken);

    userService.createAndSave(accessToken);

    OAuthAuthorization oAuthAuthorization = new OAuthAuthorization(twitter.getConfiguration());

    Query q = new Query("whatever");
    twitter.search().search(q);


    ResponseEntity<MyQueryResult> tweets = cancelledRequestService.getCancelledTweets(accessToken, oAuthAuthorization);
    return accessToken;
  }




}

