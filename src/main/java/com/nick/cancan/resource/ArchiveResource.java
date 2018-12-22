package com.nick.cancan.resource;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitter4j.HttpRequest;

import java.net.URI;
import java.util.HashMap;
import java.util.Properties;

@Component
public class ArchiveResource {

    private static String key = "Uxo8KMdQZVvlZOWh0TtysW0w9";
    private static String secret = "cZVTXrQHnZ3FUAUKtQUA5OyK1UE8lJ3IY0NjVorVzj7UU1OcE3";

    public static String FULL_ARCHIVE_URL = "https://api.twitter.com/1.1/tweets/search/fullarchive/my_env_name.json";



    private RestTemplate buildFullArchiveRequest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        return restTemplate;
    }

    private void authorizeRequest(HttpHeaders httpHeaders) {
        String oAuthHeader = "OAuth";
        Properties properties = new Properties();
        properties.put("oauth_consumer_key", key);
        properties.put("oauth_nonce", "" + (int) (Math.random() * 100000000));
        properties.put("oauth_signature_method",    "HMAC-SHA1");
        properties.put("oauth_timestamp", "" + (System.currentTimeMillis() / 1000));



    }
}
