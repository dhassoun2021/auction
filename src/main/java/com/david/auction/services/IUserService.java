package com.david.auction.services;

import com.david.auction.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> findUser (String idUser);
}
