package com.nick.cancan.util;

import com.nick.cancan.config.Twitter4jProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import twitter4j.BASE64Encoder;

@Component
public class RequestBuilderUtil {

    @Autowired
    Twitter4jProperties twitter4jProperties;


    public HttpEntity<MultiValueMap<String, String>> buildBearerRequest() throws Exception {
        String tokenEncoded = BASE64Encoder.encode((twitter4jProperties.getOauth().getConsumerKey() + ":" + twitter4jProperties.getOauth().getConsumerSecret()).getBytes("UTF-8"));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Basic " + tokenEncoded);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.add("UserDao-Agent", "Dont Get Cancelled Application v1.0");
        map.add("grant_type", "client_credentials");
        return new HttpEntity<>(map, headers);
    }

    public HttpHeaders buildAuthenticatedUserRequest(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("oauth_consumer_key", twitter4jProperties.getOauth().getConsumerKey());
        httpHeaders.add("oauth_token", token);


        return httpHeaders;
    }



}
