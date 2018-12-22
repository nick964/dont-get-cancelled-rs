package com.nick.cancan.controller;

import com.nick.cancan.service.CancelledRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;

import java.util.List;


@RestController
public class TwitterController {

  @Autowired
  CancelledRequestService cancelledRequestService;

  @CrossOrigin
  @RequestMapping("/")
  public RequestToken getTweetsFromTwit() throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();

    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:4200/authsuccess");

    return requestToken;
  }

  @CrossOrigin
  @RequestMapping("/pullTweets")
  public String getTheTest() {
    String tada = "";
    return tada;
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public AccessToken testSuccess(@RequestParam("oauth_token") String OAuthToken,
                            @RequestParam("oauth_verifier") String OAuthVerifier) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();
    twitter.get

    AccessToken accessToken = twitter.getOAuthAccessToken(OAuthVerifier);
    twitter.setOAuthAccessToken(accessToken);

    OAuthAuthorization oAuthAuthorization = new OAuthAuthorization(twitter.getConfiguration());

    List<String> tweets = cancelledRequestService.getCancelledTweets(accessToken, oAuthAuthorization);
    return accessToken;
  }




}

