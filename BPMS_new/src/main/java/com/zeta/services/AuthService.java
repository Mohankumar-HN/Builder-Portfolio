package com.zeta.service;

import com.zeta.console.App;
import com.zeta.model.ROLE_TYPE;
import com.zeta.entity.User;

import static com.zeta.console.App.users;

public class AuthService {

    public boolean register(String id){
        for(User user: users){
            if(user.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

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

