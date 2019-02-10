package com.nick.cancan.service;

import com.nick.cancan.entity.User;
import com.nick.cancan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.auth.AccessToken;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    public User createAndSaveUser(AccessToken accessToken) {
        User existingUser = userRepository.findByToken(accessToken.getToken());
        if(existingUser == null) {
            //creating new user
            User user = createUser(accessToken);
            return userRepository.save(user);
        } else {
          return existingUser;
        }
    }

    public User createUser(AccessToken accessToken) {
        User user = new User();
        user.setScreenName(accessToken.getScreenName());
        user.setToken(accessToken.getToken());
        user.setTokenSecret(accessToken.getTokenSecret());
        user.setId((int) accessToken.getUserId());
        user.setCreatedDate(new Date());
        user.setLastLogin(new Date());
        return user;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User getUser(String screenName) {
        return  userRepository.findByUsername(screenName);
    }
}
