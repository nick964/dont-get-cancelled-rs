package com.nick.cancan.model;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TokenDao {

    private String token;
    private String tokenSecret;
    private String authorizationURL;
    private String authenticationURL;
    private String screenName;
    private String isLoggedIn;

    public TokenDao(RequestToken token, String loggedIn) {
        this.token = token.getToken();
        this.tokenSecret = token.getTokenSecret();
        this.authenticationURL = token.getAuthenticationURL();
        this.authorizationURL = token.getAuthorizationURL();
        this.isLoggedIn = loggedIn;
    }

    public TokenDao(AccessToken token, String loggedIn) {
        this.token = token.getToken();
        this.tokenSecret = token.getTokenSecret();
        this.screenName = token.getScreenName();
        this.isLoggedIn = loggedIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    public String getAuthenticationURL() {
        return authenticationURL;
    }

    public void setAuthenticationURL(String authenticationURL) {
        this.authenticationURL = authenticationURL;
    }

    public String isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
