package com.nick.cancan.service;

import com.nick.cancan.model.BearerRequest;
import com.nick.cancan.resource.ArchiveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.HttpRequest;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.OAuthAuthorization;

import javax.xml.ws.Response;
import java.util.*;

@Service
public class CancelledRequestService {


    public static String FULL_ARCHIVE_URL = "https://api.twitter.com/1.1/tweets/search/fullarchive/dev.json";


    public ResponseEntity<String> getCancelledTweets(AccessToken accessToken, OAuthAuthorization oAuthAuthorization) {
        ArrayList<String> tweets = new ArrayList<>();

        List<HttpParameter> parameters = oAuthAuthorization.generateOAuthSignatureHttpParams("GET", FULL_ARCHIVE_URL);

        HttpRequest request = new HttpRequest(RequestMethod.GET,FULL_ARCHIVE_URL, buildQuery(accessToken.getScreenName()), oAuthAuthorization, getHeadersAsMap(parameters));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + getBearerToken());


        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(FULL_ARCHIVE_URL + "?query=from:nicky_robby&maxResults=20", HttpMethod.GET, entity, String.class);



        return  responseEntity;
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

    private ResponseEntity<String> getBearerToken() {
        String key = "Uxo8KMdQZVvlZOWh0TtysW0w9";
        String secret = "cZVTXrQHnZ3FUAUKtQUA5OyK1UE8lJ3IY0NjVorVzj7UU1OcE3";
        String tokenEncoded = Base64.getEncoder().encodeToString((key +":"+secret).getBytes());
        String url = "https://api.twitter.com/oauth2/token";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Bearer " + tokenEncoded);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        BearerRequest bearerRequest = new BearerRequest();

        HttpEntity<BearerRequest> bearerRequestHttpEntity = new HttpEntity<>(bearerRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, bearerRequestHttpEntity, String.class);

        return  responseEntity;

    }



}
