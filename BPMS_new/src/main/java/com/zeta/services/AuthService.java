package com.zeta.services;

import com.zeta.entity.User;

import static com.zeta.console.App.users;


public class AuthService {
    private static int userCounter = 1;

    public String generateUserId() {
        return "U" + (userCounter++);
    }

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
            if (user.getUserName().equals(name) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    //changed
    public boolean isValidPassword(String password) {

        if (password == null || password.length() < 8 ||
                !password.matches(".*[A-Z].*")||
                !password.matches(".*[a-z].*")||
                (!password.matches(".*[0-9].*")) ||
                (!password.matches(".*[@#$%^&+=!].*")))
            return false;
        return true;
    }
    public boolean usernameExists(String username) {

        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

}

