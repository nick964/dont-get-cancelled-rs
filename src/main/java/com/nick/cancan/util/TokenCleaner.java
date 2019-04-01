package com.nick.cancan.util;

import com.nick.cancan.entity.TokenSession;
import com.nick.cancan.repository.TokenSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TokenCleaner {

    private static final Logger log = LoggerFactory.getLogger(TokenCleaner.class);

    @Autowired
    TokenSessionRepository tokenSessionRepository;

    @Scheduled(cron = "0 0 0/2 * * *")
    public void cleanRequestTokens() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Date oneHourBack = cal.getTime();
        List<TokenSession> tokensToDelete = tokenSessionRepository.findExpiredTokens(oneHourBack.getTime());
        if(!CollectionUtils.isEmpty(tokensToDelete)) {
            tokenSessionRepository.deleteAll(tokensToDelete);
        }
    }
}
