package com.nick.cancan.model;

public class BearerRequest {

    private final String value ="client_credentials";

    private String grant_type;

    public BearerRequest() {
        this.grant_type = this.value;
    }

}
