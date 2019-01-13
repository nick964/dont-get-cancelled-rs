package com.nick.cancan.service;

import com.nick.cancan.model.TweetDao;

import java.util.List;

public interface OembedService {

    List<TweetDao> getOembedTweets(List<TweetDao> tweets) throws Exception;
}
