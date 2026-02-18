package com.zeta.console;

import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.TASK_STATUS;
import com.zeta.entity.User;
import com.zeta.services.AuthService;
import com.zeta.services.ProjectService;

import java.util.Scanner;
public class AllMenu {
    private static final ProjectService service = new ProjectService();
    static void projectManagerMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("\n--- PROJECT MANAGER MENU ---");
            System.out.println("1. Create Project");
            System.out.println("2. Update Project");
            System.out.println("3. See projects");
            System.out.println("4. Assign Builders");
            System.out.println("5. See Task Menu");
            System.out.println("6. Delete project");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Your Id : "+user.getId());
                    String id=scanner.nextLine();
                    String name;
                    while (true) {
                        System.out.println("Enter project name:");
                        name = scanner.nextLine();

                        if (!AuthService.validateNameandDescription(name)) {
                            System.out.println("Invalid project name. Must contain at least one letter.");
                            continue;
                        }
                        break;
                    }
                    String description;
                    while (true) {
                        System.out.println("Enter description:");
                        description = scanner.nextLine();

                        if (!AuthService.validateNameandDescription(description)) {
                            System.out.println("Invalid description. Must contain at least one letter.");
                            continue;
                        }
                        break;
                    }
                    String startDate = App.readValidDate(scanner, "Enter start date");
                    String endDate = App.readValidDate(scanner, "Enter end date");
                    String projectManagerId=user.getId();
                    User client;
                    String clientId;
                    while (true) {
                        System.out.println("Enter client username:");
                        String clientName = scanner.nextLine();

                        if (!AuthService.validateNameandDescription(clientName)) {
                            System.out.println("Invalid client name.");
                            continue;
                        }
                        client = AuthService.getUserByName(clientName);
                        if (client == null || client.getRole() != ROLE_TYPE.CLIENT) {
                            System.out.println("Client not found or not a CLIENT role. Try again.");
                            continue;
                        }
                        clientId = client.getId();
                        break;
                    }
                    PROJECT_STATUS status;
                    while (true) {
                        System.out.println("Enter project status (UPCOMING, INPROGRESS, COMPLETED):");
                        String statusInput = scanner.nextLine();

                        try {
                            status = PROJECT_STATUS.valueOf(statusInput.toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(" Invalid status! Try again.");
                        }
                    }
                    service.createProject(name, description, startDate, endDate, clientId, projectManagerId, status);
                    break;
                case 2: {
                    scanner.nextLine();
                    System.out.println("Enter project id:");
                    String updateId = scanner.nextLine();
                    if (!service.getProjects().containsKey(updateId)) {
                        System.out.println("Project not found!");
                        break;
                    }
                    PROJECT_STATUS updateStatus;
                    while (true) {
                        System.out.println("Change status to (UPCOMING, INPROGRESS, COMPLETED):");
                        String statusInput = scanner.nextLine();

                        try {
                            updateStatus = PROJECT_STATUS.valueOf(statusInput.toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status entered! Try again.");
                        }
                    }
                    service.updateStatus(updateId, updateStatus);
                    System.out.println("Project updated successfully.");
                    break;
                }
                case 3:
                    System.out.println("Showing projects");
                    service.showProjects();
                    break;
                case 4: {
                    scanner.nextLine();
                    System.out.println("Enter project ID:");
                    String pid = scanner.nextLine();
                    while (true) {
                        System.out.println("Enter builder name:");
                        String builderName = scanner.nextLine();
                        if (!AuthService.validateNameandDescription(builderName)) {
                            System.out.println("Invalid builder name.");
                            continue;
                        }
                        boolean success = service.assignBuilder(pid, builderName);
                        if (success) {
                            break;
                        }
                    }
                    break;
                }
                case 5:
                    taskMenu(scanner);
                    break;
                case 6:
                    System.out.println("enter project id to delete");
                    scanner.nextLine();
                    String deleteId=scanner.nextLine();
                    service.deleteProject(deleteId);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
     static void taskMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- TASK MENU ---");
            System.out.println("1. Create Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Back");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    String projectId;
                    while (true) {
                        System.out.println("Enter Project ID:");
                        projectId = scanner.nextLine();

                        if (!service.getProjects().containsKey(projectId)) {
                            System.out.println("Project not found. Try again.");
                            continue;
                        }
                        break;
                    }
                    String taskName;
                    while (true) {
                        System.out.println("Enter Task Name:");
                        taskName = scanner.nextLine();
                        if (!AuthService.validateNameandDescription(taskName)) {
                            System.out.println("Invalid task name.");
                            continue;
                        }
                        break;
                    }
                    String taskDesc;
                    while (true) {
                        System.out.println("Enter Task Description:");
                        taskDesc = scanner.nextLine();
                        if (!AuthService.validateNameandDescription(taskDesc)) {
                            System.out.println("Invalid task description.");
                            continue;
                        }
                        break;
                    }
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
    static void builderMenu(Scanner scanner,User user) {
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
                    if (!service.hasTasksForBuilder(builderName)) {
                        System.out.println("No tasks assigned to you.");
                        break;
                    }else{
                        System.out.println("Enter Project ID:");
                        String projectId = scanner.nextLine();
                        System.out.println("Enter Task ID:");
                        String taskId = scanner.nextLine();
                        System.out.println("Enter new task status (Upcoming / InProgress / Completed):");
                        System.out.println("Enter status:");
                        String input = scanner.nextLine();
                        TASK_STATUS status;
                        try {
                            status = TASK_STATUS.valueOf(input.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status.");
                            break;
                        }
                        service.updateTaskStatus(projectId, taskId, builderName, status);
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    static void clientMenu(Scanner scanner,User user) {
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
}