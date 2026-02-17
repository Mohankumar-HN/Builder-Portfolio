package com.zeta.Dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDao {
    private static final String FILE_NAME = System.getProperty("user.dir")+"/users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<User> users = Collections.synchronizedList(new ArrayList<>());

    public UserDao(){
        loadFromFile();
    }

    public List<User> getAllUsers(){
        return users;
    }

    public synchronized void addUser(User user){
        users.add(user);
        saveToFile();
    }

    private static void loadFromFile () {
        try {
            File file = new File(FILE_NAME);
            System.out.println("Loading from: " + file.getAbsolutePath());
            if(!file.exists()){
                System.out.println("File not found");
                file.createNewFile();
                return;
            }
            if(file.length()==0){
                System.out.println("file is empty");
                return;
            }
            List<User> loadedUsers=mapper.readValue(file, new TypeReference<List<User>>() {});
            users.clear();
            users.addAll(loadedUsers);
            System.out.println("File path: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

    }

    private static void saveToFile () {
        try {
            File file = new File(FILE_NAME);
            System.out.println("Saving to: " + file.getAbsolutePath());
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_NAME), users);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
