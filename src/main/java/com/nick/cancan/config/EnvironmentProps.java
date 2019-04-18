package com.nick.cancan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentProps {

    private final String callbackUrl;

    @Autowired
    public EnvironmentProps(@Value("${twitter.api.callbackurl}") String url) {
        this.callbackUrl = url;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
}
