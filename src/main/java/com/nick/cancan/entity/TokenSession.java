package com.nick.cancan.entity;


import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Entity
@Table(name = "token_session")
public class TokenSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="my_id")
    private Long id;

    @Column(name="PRIMARY_ID")
    private String reqSecret;

    @Column(name="REQ_TOKEN")
    private String reqToken;

    @Column(name="CREATION_TIME")
    private Long created;

    @Column(name="EXPIRY_TIME")
    private Long expiredTime;


    public String getReqSecret() {
        return reqSecret;
    }

    private void setReqSecret(String reqSecret) {
        this.reqSecret = reqSecret;
    }


    public String getReqToken() {
        return reqToken;
    }

    private void setReqToken(String reqToken) {
        this.reqToken = reqToken;
    }

    public Long getCreated() {
        return created;
    }

    private void setCreated(Long created) {
        this.created = created;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    private TokenSession () {

    }

    public TokenSession(HttpSession session, String reqToken) {
        this.setCreated(new Date().getTime());
        this.setReqSecret(session.getId());
        this.setReqToken(reqToken);
    }
    public TokenSession(String reqToken, String reqSecret) {
        this.setReqToken(reqToken);
        this.setReqSecret(reqSecret);
        this.setCreated(new Date().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
