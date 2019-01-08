package com.nick.cancan.service;

import com.nick.cancan.entity.User;
import com.nick.cancan.model.UserDao;
import com.nick.cancan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.auth.AccessToken;

import java.util.Date;

public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createAndSaveUser(AccessToken token) {
        User existingUser = userRepository.findByToken(token.getToken());
        if(existingUser == null) {
            User newUser = createUser(token);
            return userRepository.save(newUser);
        } else {
            existingUser.setLastLogin(new Date());
            return userRepository.save(existingUser);
        }

    }


    public User createUser(AccessToken accessToken) {
        User user = new User();
        user.setScreenName(accessToken.getScreenName());
        user.setToken(accessToken.getToken());
        user.setTokenSecret(accessToken.getTokenSecret());
        user.setCreatedDate(new Date());
        user.setLastLogin(new Date());
        return user;
    }


}
