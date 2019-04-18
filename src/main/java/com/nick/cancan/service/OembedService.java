package com.nick.cancan.service;

import com.nick.cancan.exception.CancelledServiceException;
import com.nick.cancan.model.TweetDao;
import twitter4j.Twitter;

import java.util.List;

public interface OembedService {

    List<TweetDao> getOembedTweets(Twitter twitter, List<TweetDao> tweets) throws CancelledServiceException;
}
