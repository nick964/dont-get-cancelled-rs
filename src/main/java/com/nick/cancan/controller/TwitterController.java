package com.nick.cancan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.cancan.entity.BadWord;
import com.nick.cancan.model.MyQueryResult;
import com.nick.cancan.model.TweetDao;
import com.nick.cancan.repository.BadWordsRepository;
import com.nick.cancan.service.CancelledRequestService;
import com.nick.cancan.service.UserServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class TwitterController {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

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
    return twitter.getOAuthRequestToken("http://localhost:4200/authsuccess");
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public @ResponseBody
    List<TweetDao> testSuccess(@RequestParam("oauth_token") String OAuthToken,
                               @RequestParam("oauth_verifier") String OAuthVerifier) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();
    AccessToken accessToken = twitter.getOAuthAccessToken(OAuthVerifier);
    twitter.setOAuthAccessToken(accessToken);
    userService.createAndSaveUser(accessToken);
    return cancelledRequestService.getCancelledTweets(accessToken);
  }

  @CrossOrigin
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public void deleteTweet(@RequestBody TweetDao tweet) throws Exception {
    LOGGER.info("Recieved tweet to delete with id: " + tweet.getId());
    cancelledRequestService.deleteTweet(tweet);
  }




}

