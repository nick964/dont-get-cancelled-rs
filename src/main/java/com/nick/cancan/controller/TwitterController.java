package com.nick.cancan.controller;


import com.nick.cancan.config.EnvironmentProps;
import com.nick.cancan.entity.TokenSession;
import com.nick.cancan.model.FireCreds;
import com.nick.cancan.model.TokenDao;
import com.nick.cancan.model.TweetDao;
import com.nick.cancan.repository.BadWordsRepository;
import com.nick.cancan.repository.TokenSessionRepository;
import com.nick.cancan.service.CancelledRequestService;
import com.nick.cancan.service.UserServiceImpl;
import com.nick.cancan.util.AllowableHosts;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "/api/cancelled", produces = "application/json")
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

  @Autowired
  TokenSessionRepository tokenSessionRepository;




  @CrossOrigin(origins = {"http://www.dontgetcancelled.com", "http://localhost:5000"})
  @RequestMapping(value = "/getToken", method = RequestMethod.GET)
  public TokenDao getTweetsFromTwit(HttpServletRequest request) throws Exception {
    Twitter twitter = twitterFactory.getInstance();
    RequestToken token = twitter.getOAuthRequestToken(environmentProps.getCallbackUrl());
    tokenSessionRepository.save(new TokenSession(token.getToken(), token.getTokenSecret()));
    return new TokenDao(token, "FALSE");
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public @ResponseBody
    List<TweetDao> testSuccess(@RequestParam("oauth_token") String OAuthToken,
                               @RequestParam("oauth_verifier") String OAuthVerifier,
                               HttpServletRequest request) throws Exception {
    TokenSession tokenSession = tokenSessionRepository.findByReqToken(OAuthToken);
    if(StringUtils.isEmpty(tokenSession)) {
      throw new Exception("Missing required session attribute to be here");
    }
    Twitter twitter = twitterFactory.getInstance();
    RequestToken requestToken = new RequestToken(tokenSession.getReqToken(), tokenSession.getReqSecret());
    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, OAuthVerifier);
    twitter.setOAuthAccessToken(accessToken);
    userService.createAndSaveUser(accessToken);
    return cancelledRequestService.getCancelledTweets(accessToken, twitter);
  }

  @CrossOrigin
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public void deleteTweet(@RequestBody TweetDao tweet) throws Exception {
    LOGGER.info("Recieved tweet to delete with id: " + tweet.getId());
    cancelledRequestService.deleteTweet(tweet);
  }

  @CrossOrigin
  @RequestMapping(value = "/herokuTest", method = RequestMethod.GET)
  public @ResponseBody String testDeploy() throws Exception {
    return  "SUCCESS";
  }






}

