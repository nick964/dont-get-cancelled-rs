package com.nick.cancan.repository;

import com.nick.cancan.entity.TokenSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {

    TokenSession findByReqToken(String reqToken);

    @Query("SELECT ts FROM TokenSession ts WHERE ts.created < :expiredTime")
    List<TokenSession> findExpiredTokens(@Param("expiredTime") Long expiredTime);


}
