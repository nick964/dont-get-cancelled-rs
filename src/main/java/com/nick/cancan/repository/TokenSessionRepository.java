package com.nick.cancan.repository;

import com.nick.cancan.entity.TokenSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenSessionRepository extends JpaRepository<TokenSession, Long> {


}
