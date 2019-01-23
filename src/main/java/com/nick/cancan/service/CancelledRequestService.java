package com.nick.cancan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.cancan.exception.CancelledServiceException;
import com.nick.cancan.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.util.*;

@Service
public class CancelledRequestService {

    @Autowired
    QueryBuilderService queryBuilderService;

    @Autowired
    OembedService oembedService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelledRequestService.class);


    public static String FULL_ARCHIVE_URL = "https://api.twitter.com/1.1/tweets/search/fullarchive/dev.json";


    public List<TweetDao> getCancelledTweets(AccessToken accessToken) throws CancelledServiceException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getBearerToken());
        MyQueryRequest query = queryBuilderService.buildQuery(accessToken.getScreenName());
        HttpEntity<MyQueryRequest> entity = new HttpEntity<>(query, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(FULL_ARCHIVE_URL, HttpMethod.POST, entity, String.class);
        List<TweetDao> tweets = mapResponseToTweets(responseEntity.getBody());
        tweets = oembedService.getOembedTweets(tweets);
        return tweets;
    }

    private List<TweetDao> mapResponseToTweets(String response) throws CancelledServiceException {
        JSONObject responseArray = new JSONObject(response);
        JSONArray results = responseArray.getJSONArray("results");
        List<TweetDao> tweets = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            for(int i = 0; i < results.length(); i++) {
                tweets.add(mapper.readValue(results.getString(i), TweetDao.class));
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: " , e);
            throw new CancelledServiceException(e);
        }
        return tweets;
    }


    private String getBearerToken() throws CancelledServiceException {
        try {
        String key = "Uxo8KMdQZVvlZOWh0TtysW0w9";
        String secret = "cZVTXrQHnZ3FUAUKtQUA5OyK1UE8lJ3IY0NjVorVzj7UU1OcE3";
        String tokenEncoded = BASE64Encoder.encode((key+":"+secret).getBytes("UTF-8"));
        String url = "https://api.twitter.com/oauth2/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Basic " + tokenEncoded);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.add("UserDao-Agent", "Dont Get Cancelled Application v1.0");
        map.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<BearerResponse> responseEntity = restTemplate.postForEntity(url,  request, BearerResponse.class);

        return  responseEntity.getBody().getAccess_token();

        } catch (Exception e) {
            LOGGER.error("ERROR: Error generating bearer token:", e);
            throw new CancelledServiceException(e);
        }


    }



}
