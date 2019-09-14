package com.nick.cancan.model;

import java.util.List;

public class CancelledTweetResponse {
    private String responseStatus;
    private String errorCause;
    private List<TweetDao>  tweets;
    private String cancelledStatus;

    public CancelledTweetResponse() {
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public List<TweetDao> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetDao> tweets) {
        this.tweets = tweets;
    }

    public String getCancelledStatus() {
        return cancelledStatus;
    }

    public void setCancelledStatus(String cancelledStatus) {
        this.cancelledStatus = cancelledStatus;
    }
}
