package com.nick.cancan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

@Component
public class CancelledSessionListener  implements HttpSessionListener {

    private static Logger log = LoggerFactory.getLogger(CancelledSessionListener.class);

    private HashMap<String, HttpSession> sessionHashMap = new HashMap<>();

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession sesh = event.getSession();
        log.info("Session created for id " + sesh.getId());
        sessionHashMap.put(sesh.getId(), sesh);
    }

    public HttpSession getSession(String sessionId) {
        return sessionHashMap.get(sessionId);
    }


}
