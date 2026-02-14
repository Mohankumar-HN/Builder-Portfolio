package com.zeta;

import java.util.Scanner;

import static com.zeta.App.users;

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

