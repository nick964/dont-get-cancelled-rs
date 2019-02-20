package com.nick.cancan.controller;


import com.nick.cancan.config.EnvironmentProps;
import com.nick.cancan.model.TweetDao;
import com.nick.cancan.repository.BadWordsRepository;
import com.nick.cancan.service.CancelledRequestService;
import com.nick.cancan.service.UserServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

  @Autowired
  EnvironmentProps environmentProps;

  @Autowired
  TwitterFactory twitterFactory;



  @CrossOrigin(origins = "http://localhost:4200")
  @RequestMapping("/")
  public RequestToken getTweetsFromTwit(HttpServletRequest request, HttpSession session) throws Exception {
    Twitter twitter = twitterFactory.getInstance();

    if(!session.isNew()) {
      session.invalidate();
    }
    RequestToken token = twitter.getOAuthRequestToken(environmentProps.getCallbackUrl());
    session.setAttribute("twitter", twitter);
    return token;
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public @ResponseBody
    List<TweetDao> testSuccess(@RequestParam("oauth_token") String OAuthToken,
                               @RequestParam("oauth_verifier") String OAuthVerifier,
                               HttpSession session) throws Exception {

    Twitter twitter = (Twitter) session.getAttribute("twitter");
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

  @CrossOrigin
  @RequestMapping(value = "/deleteTest", method = RequestMethod.POST)
  public void deleteTweetTest(@RequestBody TweetDao tweet) throws Exception {

  }




}

