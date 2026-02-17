package com.zeta.console;


import com.zeta.Dao.UserDao;
import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.User;
import com.zeta.services.AuthService;
import com.zeta.services.ProjectService;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
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
                    projectManagerMenu(scanner,loggedInUser);
                    break;
                case BUILDER:
                    builderMenu(scanner,loggedInUser);
                    break;
                case CLIENT:
                    clientMenu(scanner,loggedInUser);
                    break;
            }

        } else {
            System.out.println("Login failed (wrong role or credentials)");
        }
    }
    private static String readValidDate(Scanner scanner, String message) {

        while (true) {
            System.out.println(message + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDate.parse(input); // just checking format
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd");
            }
        }
    }

    private static  ProjectService service = new ProjectService();
    private static void projectManagerMenu(Scanner scanner,User user) {

        while (true) {
            System.out.println("\n--- PROJECT MANAGER MENU ---");
            System.out.println("1. Create Project");
            System.out.println("2. Update Project");
            System.out.println("3. see projects");
            System.out.println("4. Assign Builders");
            System.out.println("5. Create Task");
            System.out.println("6. Delete project");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Your Id : "+user.getId());
                    String id=scanner.nextLine();
                    System.out.println("Enter project name");
                    String name=scanner.nextLine();
                    System.out.println("Enter description");
                    String description=scanner.nextLine();
                    String startDate = readValidDate(scanner, "Enter start date");
                    String endDate = readValidDate(scanner, "Enter end date");
                    System.out.println("Enter client id");
                    String clientid=scanner.nextLine();
                    System.out.println("Enter project status");
                    PROJECT_STATUS status= PROJECT_STATUS.valueOf(scanner.next());
                    service.createProject(id,name,description,startDate,endDate,clientid,user.getId(),status);
                    break;
                case 2:
                    System.out.println("Enter project id");
                    String updateid=scanner.next();
                    System.out.println("Change status to--UPCOMING,INPROGRESS,COMPLETED");
                    String statusInput = scanner.next();
                    try {
                        PROJECT_STATUS updatestatus =
                                PROJECT_STATUS.valueOf(statusInput.toUpperCase());

                        service.updateStatus(updateid, updatestatus);
                        System.out.println("Updated project...");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status entered!");
                    }
                    break;
                case 3:
                    System.out.println("Showing projects");
                    service.showProjects();
                    break;
                case 4:
                    System.out.println("Enter project ID");
                    String pid=scanner.next();
                    System.out.println("Enter builder name");
                    String buildername=scanner.next();
                    service.assignBuilder(pid,buildername);
                    break;
                case 5:
                    taskMenu(scanner);
                    break;
                case 6:
                    System.out.println("enter project id to delete");
                    scanner.nextLine();
                    String deleteid=scanner.nextLine();
                    service.deleteProject(deleteid);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void taskMenu(Scanner scanner) {

        while (true) {

            System.out.println("\n--- TASK MENU ---");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Back");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter Project ID:");
                    String projectId = scanner.nextLine();
                    String taskId = service.generateTaskId();
                    System.out.println("Enter Task ID: "+taskId);

                    System.out.println("Enter Task Name:");
                    String taskName = scanner.nextLine();

                    System.out.println("Enter Task Description:");
                    String taskDesc = scanner.nextLine();

                    System.out.println("Enter Builder Name:");
                    String builderName = scanner.nextLine();

                    service.createTask(projectId,
                            taskName,
                            taskDesc,
                            builderName);
                    break;

                case 2:
                    service.showAllTasks();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void builderMenu(Scanner scanner,User user) {
        System.out.println("Hello Builder!!");
        String builderId = user.getId();
        String builderName = user.getUserName();
        System.out.println("Name : "+builderName);
        System.out.println("ID : " +builderId);

        while (true) {
            System.out.println("\n--- BUILDER MENU ---");
            System.out.println("1. View Assigned Projects");
            System.out.println("2. View My Tasks");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.showBuilderProjects(builderName);
                    break;
                case 2:
                    service.showBuilderTasks(builderName);
                    break;
                case 3:
                    System.out.println("Enter Project ID:");
                    String projectId = scanner.nextLine();
                    System.out.println("Enter Task ID:");
                    String taskId = scanner.nextLine();
                    service.updateTaskStatus(projectId, taskId, builderName);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void clientMenu(Scanner scanner,User user) {
        String clientId = user.getId();

        System.out.println("Your Client ID: "+clientId);

        while (true) {

            System.out.println("\n--- CLIENT MENU ---");
            System.out.println("1. View My Projects");
            System.out.println("2. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    service.showClientProjects(clientId);
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }



    private static void addUser (Scanner scanner){
        AuthService authService = new AuthService();
        //changed

        String username;

        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();

            if (username.trim().isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            if (authService.usernameExists(username)) {
                System.out.println("Username already taken. Try another.");
            }
            else {
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
                System.out.println("Invalid role. Please try again.");
            }
        }
        String id = authService.generateUserId();
        System.out.println("Generated User ID: " + id);

        if(authService.register(id)){
            System.out.println("User already registered");
            return;
        }
        userDao.addUser(new User(id,username, password, role));
        System.out.println("Successfull Registration");
    }


}




