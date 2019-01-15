package com.nick.cancan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    public List<TweetDao> getCancelledTweets(AccessToken accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + getBearerToken());



        MyQueryRequest query = queryBuilderService.buildQuery(accessToken.getScreenName());

        HttpEntity<MyQueryRequest> entity = new HttpEntity<>(query, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(FULL_ARCHIVE_URL, HttpMethod.POST, entity, String.class);

        JSONObject responseArray = new JSONObject(responseEntity.getBody());
        JSONArray results = responseArray.getJSONArray("results");

        List<TweetDao> tweets = new ArrayList<>();


        ObjectMapper mapper = new ObjectMapper();
        try{
            for(int i = 0; i < results.length(); i++) {
                tweets.add(mapper.readValue(results.getString(i), TweetDao.class));
            }
            tweets = oembedService.getOembedTweets(tweets);
        } catch (Exception e) {
            LOGGER.error("ERROR: " , e);
        }

        return tweets;
    }


    private HttpParameter[] buildQuery(String user) {
        HttpParameter[] httpParams = new HttpParameter[1];

        String query = "";

        query = query + "from:" + StringUtils.trimAllWhitespace(user) + " ";
        query = query + " fuck";
        HttpParameter httpParameter = new HttpParameter("q",query);
        httpParams[0] = httpParameter;
        return httpParams;

    }

    private ResultsObject mapResponse(ResponseEntity<String> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResultsObject results = mapper.readValue(response.getBody(), ResultsObject.class);
            return results;
        } catch(Exception e) {
            LOGGER.error("error parsing response " + e);
            return null;
        }

    }

    private Map<String, String> getHeadersAsMap(List<HttpParameter> parameters) {
        Map<String, String> map = new HashMap<>();
        for(HttpParameter param : parameters) {
            map.put(param.getName(), param.getValue());
        }
        return map;
    }

    private String getAuthAsParam(String token, List<HttpParameter> params) {
        String auth = "OAuth ";
        auth += "oauth_token=\"" + token + "\",";
        int i = 0;
        for(HttpParameter parameter : params) {
            if(!StringUtils.isEmpty(parameter.getName()) && !StringUtils.isEmpty(parameter.getValue())) {
                auth += parameter.getName() + "=\"" + parameter.getValue() + "\"";
                if (!(i++ == params.size() - 1)) {
                    auth += ",";
                }
            }
        }



        return auth;
    }

    private String getBearerToken() {
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

            return null;
        }


    }



}
