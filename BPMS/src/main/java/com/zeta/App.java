package com.zeta;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static sun.security.jgss.GSSUtil.login;

public class App {
    private static final String FILE_NAME = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Welcome to Builder Porfolio system----");

        while(true) {
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit");
            int n = scanner.nextInt();
            switch (n) {
                case 1:
                    addUser(scanner);
                    saveToFile();
                    break;
                case 2:
                    login(scanner);
                    break;
                default:
                    System.out.println("Logout successful");

            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter your name:");
        String name=scanner.next();
        System.out.println("Enter your password");
        String password=scanner.next();
        ROLE_TYPE role = null;

        while (role == null) {
            System.out.println("Enter your role (PROJECTMANAGER, BUILDER, CLIENT): ");
            String roleInput = scanner.next();

            try {
                role = ROLE_TYPE.valueOf(roleInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid role. Please try again.");
            }
        }

        AuthService authService=new AuthService();
        if(authService.logIn(name,password,role)){
            System.out.println("login successful");
        }else{
            System.out.println("login failed");
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

            ROLE_TYPE role = null;

            while (role == null) {
                System.out.println("Enter your role (PROJECTMANAGER, BUILDER, CLIENT): ");
                String roleInput = scanner.next();

                try {
                    role = ROLE_TYPE.valueOf(roleInput.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid role. Please try again.");
                }
            }



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

