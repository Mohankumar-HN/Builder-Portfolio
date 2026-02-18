package com.zeta.Dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class UserDao {
    private static final String FILE_NAME = System.getProperty("user.dir") + "/users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<User> users = Collections.synchronizedList(new ArrayList<>());
    static final Logger logger = Logger.getLogger(UserDao.class.getName());

    public UserDao() {
        loadFromFile();
    }

    public synchronized void addUser(User user) {
        users.add(user);
        saveToFile();
    }

    private static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                logger.info("File not found");
                file.createNewFile();
                return;
            }
            if (file.length() == 0) {
                logger.info("file is empty");
                return;
            }
            List<User> loadedUsers = mapper.readValue(file, new TypeReference<List<User>>() {
            });
            users.clear();
            users.addAll(loadedUsers);

        } catch (IOException e) {
            logger.info("Error loading data: " + e.getMessage());
        }

    }
    private static void saveToFile() {
        try {
            File file = new File(FILE_NAME);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), users);
        } catch (IOException e) {
            logger.info("Error saving data: " + e.getMessage());
        }
    }
}