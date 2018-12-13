package com.nick.cancan.model;

public class User {

    Long userId;

    String screenName;

    String token;

    String accessToken;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return accessToken;
    }

    public void setTokenSecret(String tokenSecret) {
        this.accessToken = tokenSecret;
    }
}
