package com.nick.cancan;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;
import twitter4j.auth.RequestToken;

@RestController
public class TwitterController {

  @RequestMapping("/")
  public String getTweetsFromTwit() throws Exception {
    String toReturn = "";

    Twitter twitter = TwitterFactory.getSingleton();

    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8080/success");



    Query query = new Query("from:nicky_robby ");


    QueryResult result = twitter.search(query);


    for(Status status : result.getTweets()) {
      toReturn += " " + status.getText();
    }

    return toReturn;


  }

  @RequestMapping("/success")
  public String testSuccess() {
    return "yep";
  }


}

