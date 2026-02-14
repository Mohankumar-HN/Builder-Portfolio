package com.zeta.service;

import com.zeta.entity.User;

import static com.zeta.console.App.users;

public class AuthService {

    public User logIn(String name, String password) {
        for (User user : users) {
            if (user.getName().equals(name) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}

