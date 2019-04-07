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




  @CrossOrigin
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public TokenDao getTweetsFromTwit(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession();
    if(!session.isNew()) {
      Twitter twitterLoggedIn = (Twitter) session.getAttribute("twitter");
      AccessToken accessToken = twitterLoggedIn.getOAuthAccessToken();
      return new TokenDao(accessToken, "TRUE");
    }
    Twitter twitter = twitterFactory.getInstance();
    RequestToken token = twitter.getOAuthRequestToken(environmentProps.getCallbackUrl());

    tokenSessionRepository.save(new TokenSession(token.getToken(), token.getTokenSecret()));
    TokenDao tokenDao = new TokenDao(token, "FALSE");
    return tokenDao;
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
  @RequestMapping(value = "/getTweets", method = RequestMethod.POST)
  public @ResponseBody
  List<TweetDao> getTweets(@RequestBody FireCreds fireCredentials) throws Exception {
    AccessToken token = new AccessToken(fireCredentials.getAccessToken(), fireCredentials.getSecret());
    Twitter twitter = twitterFactory.getInstance(token);
    return cancelledRequestService.getCancelledTweets(token, twitter);
  }

  @CrossOrigin
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public void deleteTweet(@RequestBody TweetDao tweet) throws Exception {
    LOGGER.info("Recieved tweet to delete with id: " + tweet.getId());
    cancelledRequestService.deleteTweet(tweet);
  }







}

