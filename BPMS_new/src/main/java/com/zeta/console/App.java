package com.zeta.console;
import com.zeta.Dao.UserDao;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.User;
import com.zeta.services.AuthService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.zeta.console.AllMenu.*;
public class App {
    static final Logger logger= Logger.getLogger(App.class.getName());
    private static final UserDao userDao = new UserDao();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Welcome to Builder Portfolio system----");

        while (true) {
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit");

            try {
                int n = scanner.nextInt();
                scanner.nextLine();

                switch (n) {
                    case 1:
                        addUser(scanner);
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
            } catch (java.util.InputMismatchException inputMismatchException) {
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
                    logger.info("Invalid role selection.");
                    return;
            }
        } catch (InputMismatchException inputMismatchException) {
            logger.warning("Invalid input");
            scanner.nextLine();
            return;
        }

        System.out.println("Enter your name:");
        String name = scanner.next();

        System.out.println("Enter your password:");
        String password = scanner.next();

        AuthService authService = new AuthService();
        User loggedInUser = authService.logIn(name, password);

        if (loggedInUser != null && loggedInUser.getRole() == selectedRole) {
            logger.info("Login successful");

            switch (selectedRole) {
                case PROJECTMANAGER:
                    projectManagerMenu(scanner, loggedInUser);
                    break;
                case BUILDER:
                    builderMenu(scanner, loggedInUser);
                    break;
                case CLIENT:
                    clientMenu(scanner, loggedInUser);
                    break;
            }

        } else {
            logger.warning("Login failed (wrong role or credentials)");
        }
    }

    static String readValidDate(Scanner scanner, String message) {

        while (true) {
            System.out.println(message + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDate.parse(input);
                return input;
            } catch (DateTimeParseException e) {
                logger.info("Invalid date format! Please use yyyy-MM-dd");
            }
        }
    }

    private static void addUser(Scanner scanner) {
        AuthService authService = new AuthService();
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if (!AuthService.validateNameandDescription(username)) {
                logger.info("Username must contain at least one letter and cannot be empty.");
                continue;
            }
            if (authService.checkDuplicateUser(username)) {
                logger.info("Username already taken. Try another.");
            } else {
                break;
            }
        }
        String password;
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();

            if (authService.isValidPassword(password)) {
                break;
            } else {
                System.out.println("Password must contain:");
                System.out.println("- Minimum 8 characters");
                System.out.println("- At least 1 uppercase letter");
                System.out.println("- At least 1 lowercase letter");
                System.out.println("- At least 1 digit");
                System.out.println("- At least 1 special character (@#$%^&+=!)");
            }
        }
        ROLE_TYPE role = null;
        while (role == null) {
            System.out.println("Enter your role (PROJECTMANAGER, BUILDER, CLIENT): ");
            String roleInput = scanner.next();
            try {
                role = ROLE_TYPE.valueOf(roleInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.info("Invalid role. Please try again.");
            }
        }
        String id = authService.generateUserId();
        System.out.println("Generated User ID: " + id);

        if (authService.checkDuplicateUser(id)) {
            System.out.println("User already registered");
            return;
        }
        userDao.addUser(new User(id, username, password, role));
        System.out.println("Successfull Registration");
    }
}