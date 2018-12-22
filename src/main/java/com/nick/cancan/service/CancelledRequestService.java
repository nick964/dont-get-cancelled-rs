package com.nick.cancan.service;

import com.nick.cancan.resource.ArchiveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import twitter4j.HttpParameter;
import twitter4j.HttpRequest;
import twitter4j.RequestMethod;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;

import java.util.*;

@Service
public class CancelledRequestService {


    public static String FULL_ARCHIVE_URL = "https://api.twitter.com/1.1/tweets/search/fullarchive/dev.json";


    public List<String> getCancelledTweets(AccessToken accessToken, OAuthAuthorization oAuthAuthorization) {
        ArrayList<String> tweets = new ArrayList<>();

        List<HttpParameter> parameters = oAuthAuthorization.generateOAuthSignatureHttpParams("GET", FULL_ARCHIVE_URL);

        HttpRequest request = new HttpRequest(RequestMethod.GET,FULL_ARCHIVE_URL, buildQuery(accessToken.getScreenName()), oAuthAuthorization, getHeadersAsMap(parameters));


        return  tweets;
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



}
