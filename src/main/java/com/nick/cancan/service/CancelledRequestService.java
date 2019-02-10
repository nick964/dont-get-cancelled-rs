package com.nick.cancan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.cancan.entity.User;
import com.nick.cancan.exception.CancelledServiceException;
import com.nick.cancan.model.*;
import com.nick.cancan.util.RequestBuilderUtil;
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

    @Autowired
    RequestBuilderUtil requestBuilderUtil;

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelledRequestService.class);

    public static String BEARER_URL = "https://api.twitter.com/oauth2/token";
    public static String FULL_ARCHIVE_URL = "https://api.twitter.com/1.1/tweets/search/fullarchive/dev.json";
    public static String DELETE_URL = "https://api.twitter.com/1.1/statuses/destroy/";


    public List<TweetDao> getCancelledTweets(AccessToken accessToken) throws CancelledServiceException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getBearerToken());
        MyQueryRequest query = queryBuilderService.buildQuery(accessToken.getScreenName());
        HttpEntity<MyQueryRequest> entity = new HttpEntity<>(query, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(FULL_ARCHIVE_URL, HttpMethod.POST, entity, String.class);
        List<TweetDao> tweets = mapResponseToTweets(responseEntity.getBody());
        //tweets = oembedService.getOembedTweets(tweets);
        return tweets;
    }

    public void deleteTweet(TweetDao tweet) throws CancelledServiceException {
        RestTemplate restTemplate = new RestTemplate();
        User user = userService.getUser(tweet.getUserId());
        HttpHeaders headers = requestBuilderUtil.buildAuthenticatedUserRequest(user.getToken());
        String deleteUrl = DELETE_URL + tweet.getId() + ".json";
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(deleteUrl, HttpMethod.POST, entity, String.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("Error deleting tweet", responseEntity.getBody());
        }
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
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<MultiValueMap<String, String>> request = requestBuilderUtil.buildBearerRequest();
        ResponseEntity<BearerResponse> responseEntity = restTemplate.postForEntity(BEARER_URL,  request, BearerResponse.class);
        return  responseEntity.getBody().getAccess_token();
        } catch (Exception e) {
            LOGGER.error("ERROR: Error generating bearer token:", e);
            throw new CancelledServiceException(e);
        }
    }

}
