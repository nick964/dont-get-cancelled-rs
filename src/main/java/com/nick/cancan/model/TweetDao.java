package com.nick.cancan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetDao {

    private static  String URL_PREFIX = "https://twitter.com/";
    private static  String URL_POSTFIXX = "/status/";

    private Long id;
    private String text;
    private String created_at;
    private String html;
    private String url;
    private UserDao user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        if(StringUtils.isEmpty(this.user.getScreen_name()) || StringUtils.isEmpty(this.id)) {
            return "";
        }
        return URL_PREFIX + this.user.screen_name + URL_POSTFIXX + this.id;
    }
}
