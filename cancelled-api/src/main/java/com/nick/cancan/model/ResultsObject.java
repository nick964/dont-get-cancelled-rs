package com.nick.cancan.model;

import java.util.List;

public class ResultsObject {
    public List<TweetDao> results;

    public ResultsObject(List<TweetDao> tweetDaoList) {
        this.results = tweetDaoList;
    }

    public List<TweetDao> getTweetDaoList() {
        return results;
    }

    public void setTweetDaoList(List<TweetDao> tweetDaoList) {
        this.results = tweetDaoList;
    }
}
