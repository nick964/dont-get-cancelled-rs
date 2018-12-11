package com.nick.cancan.controller;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;



@RestController
public class TwitterController {

  @CrossOrigin
  @RequestMapping("/")
  public RequestToken getTweetsFromTwit() throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();

    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8080/success");

    return requestToken;
  }

  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public String testSuccess(@RequestParam("oauth_token") String OAuthToken,
                            @RequestParam("oauth_verifier") String OAuthVerifier) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();
    AccessToken accessToken = twitter.getOAuthAccessToken(OAuthVerifier);
    String toReturn = "Screen name is " + accessToken.getScreenName() + " and user ID is " + accessToken.getUserId();
    twitter.setOAuthAccessToken(accessToken);

    Query q = new Query();
    q.setQuery(buildQuery(accessToken.getScreenName()));
    QueryResult res = twitter.search().search(q);
    return toReturn;
  }

  private String buildQuery(String user) {
    String query = "";

    query = query + "from:" + StringUtils.trimAllWhitespace(user) + " ";
    query = query + "";
    return query;

  }


}

