package com.zeta.console;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta.service.AuthService;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    private static final String FILE_NAME = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<User> users = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Welcome to Builder Portfolio system----");

        while (true) {
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit");

            try {
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
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }catch (java.util.InputMismatchException inputMismatchException){
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }

    private static void login(Scanner scanner) {

            System.out.println("Select your role:");
            System.out.println("1. Project Manager");
            System.out.println("2. Builder");
            System.out.println("3. Client");

        ROLE_TYPE selectedRole = null;
            try {
                int roleChoice = scanner.nextInt();
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
            }catch (InputMismatchException inputMismatchException){
                System.out.println("Invalid input");
                scanner.nextLine();
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
                    ProjectService service = new ProjectService();
                    service.createProject("1","name","desc","01-02-2026","01-02-2027","C1","Pm1");
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
            System.out.println("0 : give project");
            System.out.println("1. View My Projects");
            System.out.println("2. Give Feedback");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Taking project from client");
                    StoreProjects.addProjects();

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
            System.out.println("Enter your id");
            String id=scanner.next();

            AuthService authService=new AuthService();
            if(authService.register(id)){
                System.out.println("User already registered");
                return;
            }
            users.add(new User(id,name, password, role));
            System.out.println("Successfull Registration");
        }

    private static void loadFromFile () {
            try {
                File file = new File(FILE_NAME);
                if(!file.exists()){
                    System.out.println("File not found");
                    file.createNewFile();
                    return;
                }
                if(file.length()==0){
                    System.out.println("file is empty");
                    return;
                }
                users = mapper.readValue(
                            file,
                            new TypeReference<List<User>>() {
                            }

                    );
                System.out.println("File path: " + file.getAbsolutePath());


            } catch (IOException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }

        }
}




