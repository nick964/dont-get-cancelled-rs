package com.nick.cancan.model;

import twitter4j.QueryResult;

public class MyQueryRequest {
    private String query;

    public MyQueryRequest(String query){
        this.query = query;
    }

    public MyQueryRequest() {
        this.query = "";
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
