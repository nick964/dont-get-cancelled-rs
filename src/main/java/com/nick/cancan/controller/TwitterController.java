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

    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:4200/authsuccess");

    return requestToken;
  }

  @CrossOrigin
  @RequestMapping("/test")
  public String getTheTest() {
    String tada = "";
    return tada;
  }

  @RequestMapping(value = "/success", method = RequestMethod.GET)
  public AccessToken testSuccess(@RequestParam("oauth_token") String OAuthToken,
                            @RequestParam("oauth_verifier") String OAuthVerifier) throws Exception {

    Twitter twitter = TwitterFactory.getSingleton();
    AccessToken accessToken = twitter.getOAuthAccessToken(OAuthVerifier);
    twitter.setOAuthAccessToken(accessToken);

    Query q = new Query();
    q.setQuery(buildQuery(accessToken.getScreenName()));
    QueryResult res = twitter.search().search(q);
    return accessToken;
  }

  private String buildQuery(String user) {
    String query = "";

    query = query + "from:" + StringUtils.trimAllWhitespace(user) + " ";
    query = query + "";
    return query;

  }


}

