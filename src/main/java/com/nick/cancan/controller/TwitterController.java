package com.nick.cancan.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.cancan.config.EnvironmentProps;
import com.nick.cancan.entity.TokenSession;
import com.nick.cancan.model.CancelledTweetResponse;
import com.nick.cancan.model.TokenDao;
import com.nick.cancan.model.TweetDao;
import com.nick.cancan.repository.WordsRepository;
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
import java.util.List;


@RestController
@RequestMapping(value = "/api/cancelled", produces = "application/json")
public class TwitterController {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);
  private static final String ERROR_RESPONSE = "ERROR";
  private static final String SUCCESS_RESPONSE = "SUCCESS";

  @Autowired
  CancelledRequestService cancelledRequestService;

  @Autowired
  WordsRepository wordsRepository;

  @Autowired
  UserServiceImpl userService;

  @Autowired
  EnvironmentProps environmentProps;

  @Autowired
  TwitterFactory twitterFactory;

  @Autowired
  TokenSessionRepository tokenSessionRepository;




  @CrossOrigin
  @RequestMapping(value = "/getToken", method = RequestMethod.GET)
  public TokenDao getTweetsFromTwit(HttpServletRequest request) throws Exception {
    Twitter twitter = twitterFactory.getInstance();
    RequestToken token = twitter.getOAuthRequestToken(environmentProps.getCallbackUrl());
    LOGGER.info("Callback url is " + environmentProps.getCallbackUrl());
    tokenSessionRepository.save(new TokenSession(token.getToken(), token.getTokenSecret()));
    return new TokenDao(token, "FALSE");
  }

  @CrossOrigin
  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public @ResponseBody
    CancelledTweetResponse testSuccess(@RequestParam("oauth_token") String OAuthToken,
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
    CancelledTweetResponse response =  createSuccessResponse(cancelledRequestService.getCancelledTweets(accessToken, twitter));
    System.out.println(response);
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(response));
    return response;
  }


  @ExceptionHandler(value = Exception.class)
  public CancelledTweetResponse handleException(Exception e) {
    LOGGER.error("Error calling Don't Get Cancelled service", e);
    return createErrorResponse(e);
  }


  @CrossOrigin
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public void deleteTweet(@RequestBody TweetDao tweet) throws Exception {
    LOGGER.info("Recieved tweet to delete with id: " + tweet.getId());
    cancelledRequestService.deleteTweet(tweet);
  }

  private CancelledTweetResponse createSuccessResponse(List<TweetDao> tweets) {
    CancelledTweetResponse response = new CancelledTweetResponse();
    response.setResponseStatus(SUCCESS_RESPONSE);
    response.setTweets(tweets);
    return response;
  }

  private CancelledTweetResponse createErrorResponse(Exception e) {
    CancelledTweetResponse response = new CancelledTweetResponse();
    response.setResponseStatus(ERROR_RESPONSE);
    response.setErrorCause(e.getLocalizedMessage());
    return response;
  }





}

