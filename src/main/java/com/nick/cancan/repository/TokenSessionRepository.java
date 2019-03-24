package com.nick.cancan.repository;

import com.nick.cancan.entity.TokenSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {

    TokenSession findByReqToken(String reqToken);


}
