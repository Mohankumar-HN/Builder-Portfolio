package com.zeta;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String FILE_NAME = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Welcome to Builder Porfolio system----");

        while(true) {
            System.out.println("Enter 1: Register 2:Login");
            int n = scanner.nextInt();
            switch (n) {
                case 1:
                    addUser(scanner);
                    saveToFile();
                    break;
                default:
                    System.out.println("Logout successful");

            }
        }
    }

        private static void saveToFile () {
            try {
                mapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File(FILE_NAME), users);
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }


        private static void addUser (Scanner scanner){
            System.out.print("Enter name: ");
            String name = scanner.next();
            scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.print("Enter Role");
            ROLE_TYPE role = ROLE_TYPE.valueOf(scanner.next());

            users.add(new User(name, password, role));
            System.out.println("Successfull Registration");
        }

        private static void loadFromFile () {
            try {
                File file = new File(FILE_NAME);
                if (file.exists()) {
                    users = mapper.readValue(
                            file,
                            new TypeReference<List<User>>() {
                            }
                    );
                }
            } catch (IOException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }

        }
    }

