package com.nick.cancan.model;

import java.util.Date;

public class UserDao {

    Long id;

    String screen_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }
}
