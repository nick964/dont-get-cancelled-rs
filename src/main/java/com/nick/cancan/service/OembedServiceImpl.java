package com.nick.cancan.service;

import com.nick.cancan.model.TweetDao;
import org.springframework.stereotype.Service;
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.List;

@Service
public class OembedServiceImpl implements  OembedService {

    @Override
    public List<TweetDao> getOembedTweets(List<TweetDao> tweets) throws Exception {
        Twitter twitter = TwitterFactory.getSingleton();
        for(TweetDao tweet : tweets) {
            OEmbedRequest req = new OEmbedRequest(tweet.getId(), tweet.getUrl());
            OEmbed oEmbed = twitter.tweets().getOEmbed(req);
            tweet.setHtml(oEmbed.getHtml());
        }

        return tweets;
    }
}
