package com.nick.cancan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetDao {

    private static  String URL_PREFIX = "https://twitter.com/";
    private static  String URL_POSTFIXX = "/status/";

    private String id;
    private String text;
    private String created_at;
    private String html;
    private String url;
    private String userId;
    private String screenName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getHtml() {
        return html;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        if(StringUtils.isEmpty(this.getScreenName()) || StringUtils.isEmpty(this.getUserId())) {
            return "";
        }
        return URL_PREFIX + this.getScreenName() + URL_POSTFIXX + this.getId();
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("user")
    private void unpackNested(Map<String,Object> user) {
        this.userId= user.get("id").toString();
        this.screenName = (String) user.get("screen_name");
    }

}