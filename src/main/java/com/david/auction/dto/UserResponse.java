package com.david.auction.dto;

import com.david.auction.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("winner")
    private  User user ;

    public UserResponse () {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
