package com.nick.cancan.service;

import com.nick.cancan.exception.CancelledServiceException;
import com.nick.cancan.model.TweetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.List;

@Service
public class OembedServiceImpl implements  OembedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OembedServiceImpl.class);

    @Override
    public List<TweetDao> getOembedTweets(Twitter twitter, List<TweetDao> tweets) throws CancelledServiceException {
        try {
            for (TweetDao tweet : tweets) {
                OEmbedRequest req = new OEmbedRequest(Long.parseLong(tweet.getId()), tweet.getUrl());
                OEmbed oEmbed = twitter.tweets().getOEmbed(req);
                tweet.setHtml(oEmbed.getHtml());
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching oembed html tweets", e);
            throw new CancelledServiceException(e);
        }
        return tweets;
    }


}
