package com.nick.cancan.service;

import com.nick.cancan.entity.User;
import com.nick.cancan.model.UserDao;
import com.nick.cancan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.auth.AccessToken;

import java.util.Date;

public interface UserService {

    User createAndSaveUser(AccessToken token);


    User createUser(AccessToken accessToken);

    User getUser(Long userId);

    User getUser(String screenName);


}
