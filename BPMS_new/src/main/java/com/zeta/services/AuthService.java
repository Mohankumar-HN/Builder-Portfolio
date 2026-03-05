package com.zeta.services;

import com.zeta.Dao.UserDao;
import com.zeta.entity.User;
 import java.util.regex.Pattern;

import static com.zeta.Dao.UserDao.users;

public class AuthService {
    public String generateUserId() {
        int max = 0;
        for (User user : UserDao.users) {
            String id = user.getId();
            if (id != null && id.startsWith("U")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > max) {
                    max = num;
                }
            }
        }
        return "U" + (max + 1);
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
  

private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");

public boolean isValidPassword(String password) {

    if (password == null) {
        return false;
    }

    return PASSWORD_PATTERN.matcher(password).matches();
}
    public boolean checkDuplicateUser(String username) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    public static boolean validateNameandDescription(String input) {
        if (input == null) return false;
        input = input.trim();
        if (input.isEmpty()) return false;
        if (!input.matches(".*[a-zA-Z].*")) return false;
        return true;
    }
    public static User getUserByName(String name) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }
}