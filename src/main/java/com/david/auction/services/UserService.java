package com.david.auction.services;

import com.david.auction.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserService implements IUserService {

    private static Map<String,User> users = new ConcurrentHashMap<>();

    static {
        users.put("idUser1", new User("idUsers1","DUPONT"));
        users.put("idUser2", new User("idUsers2","DUPUIS"));
        users.put("idUser3", new User("idUsers3","MARTIN"));
    }
    public Optional<User> findUser (String idUser) {
       User user = users.get(idUser);
       if (user == null) {
           return Optional.empty();
       }
       return Optional.of(user);
    }
}
