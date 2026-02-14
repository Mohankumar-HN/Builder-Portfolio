package com.zeta;

import java.util.Scanner;

import static com.zeta.App.users;

public class AuthService {
    public boolean logIn(String name,String password,ROLE_TYPE role){
        for (User user : users) {
            if (user.getName().equals(name) &&
                    user.getPassword().equals(password) && user.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }

}
