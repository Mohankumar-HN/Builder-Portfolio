package com.zeta;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {
    private static final String FILE_NAME = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    static List<User> users = new ArrayList<>();

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Welcome to Builder Portfolio system----");

        while (true) {
            System.out.println("1: Register");
            System.out.println("2: Select Role");
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
                case 3:
                    System.out.println("Exiting...");
                    return;  // 🔥 Proper exit
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void login(Scanner scanner) {

            System.out.println("Select your role:");
            System.out.println("1. Project Manager");
            System.out.println("2. Builder");
            System.out.println("3. Client");

            int roleChoice = scanner.nextInt();

            ROLE_TYPE selectedRole = null;

            switch (roleChoice) {
                case 1:
                    selectedRole = ROLE_TYPE.PROJECTMANAGER;
                    break;
                case 2:
                    selectedRole = ROLE_TYPE.BUILDER;
                    break;
                case 3:
                    selectedRole = ROLE_TYPE.CLIENT;
                    break;
                default:
                    System.out.println("Invalid role selection.");
                    return;
            }

            System.out.println("Enter your name:");
            String name = scanner.next();

            System.out.println("Enter your password:");
            String password = scanner.next();

            AuthService authService = new AuthService();
            User loggedInUser = authService.logIn(name, password);

            if (loggedInUser != null && loggedInUser.getRole() == selectedRole) {
                System.out.println("Login successful");

                switch (selectedRole) {
                    case PROJECTMANAGER:
                        projectManagerMenu(scanner);
                        break;
                    case BUILDER:
                        builderMenu(scanner);
                        break;
                    case CLIENT:
                        clientMenu(scanner);
                        break;
                }

            } else {
                System.out.println("Login failed (wrong role or credentials)");
            }
        }

    private static void projectManagerMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- PROJECT MANAGER MENU ---");
            System.out.println("1. Create Project");
            System.out.println("2. View Projects");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Project created!");
                    break;
                case 2:
                    System.out.println("Showing projects...");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void builderMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- BUILDER MENU ---");
            System.out.println("1. View Assigned Projects");
            System.out.println("2. Update Status");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Showing assigned projects...");
                    break;
                case 2:
                    System.out.println("Status updated!");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void clientMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- CLIENT MENU ---");
            System.out.println("1. View My Projects");
            System.out.println("2. Give Feedback");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Showing your projects...");
                    break;
                case 2:
                    System.out.println("Feedback submitted!");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
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

            users.add(new User(generateUUID(),name, password, role));
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




