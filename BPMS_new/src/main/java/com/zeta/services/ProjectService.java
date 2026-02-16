
package com.zeta.services;
import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.Project;
import com.zeta.entity.TASK_STATUS;
import com.zeta.entity.Task;

import java.util.HashMap;
import java.util.Map;

public class ProjectService {
    final Map<String,Project> projects = new HashMap<>();
    private static int projectCounter = 1;
    private String generateProjectId() {
        return "P" + (projectCounter++);
    }
    public synchronized void createProject(String id, String name, String description,
                                           String start, String end, String clientId,PROJECT_STATUS status) {
        id = generateProjectId();
        Project project = new Project(id, name, description, start, end, clientId,status);
        projects.put(id, project);
        System.out.println("Project Created Successfully!");
    }
    public synchronized void updateStatus(String projectId, PROJECT_STATUS newStatus) {
        Project project = projects.get(projectId);
        if (project != null) {
            project.setStatus(newStatus);
            System.out.println("Status Updated Successfully!");
        } else {
            System.out.println("Project Not Found!");
        }
    }
    public synchronized void showProjects() {
        if(projects.isEmpty()){
            System.out.println("No project found");
        }else {
            for (Project project : projects.values()) {
                System.out.println(project);
            }
        }

    }

    public synchronized void deleteProject(String id){
        if(projects.containsKey(id)){
            projects.remove(id);
            System.out.println("Project deleted successfully");
        }else{
            System.out.println("project not found");
        }
    }
    public  synchronized void assignBuilder(String id,String name){
        Project project=projects.get(id);
        if(project!=null){
            project.setBuilderName(name);
            System.out.println("Builder assigned successfully");
        }else{
            System.out.println("project not found");
        }
    }
    private static int taskCounter = 1;

    public String generateTaskId() {
        return "T" + (taskCounter++);
    }

    public synchronized void createTask(String projectId, String taskId, String taskName,
                                        String description, String builderName) {
        Project project = projects.get(projectId);
        if (project != null) {
            taskId=generateTaskId();
            Task task = new Task(taskId, taskName,
                    description, builderName);
            project.getTasks().add(task);
            System.out.println("Task Id : "+taskId );
            System.out.println("Task created successfully!");
        } else {
            System.out.println("Project not found!");
        }
    }
    public synchronized void showAllTasks() {
        for (Project project : projects.values()) {
            System.out.println("Project: " + project.getProjectId());
            for (Task task : project.getTasks()) {
                System.out.println(task);
            }
        }
    }
    public synchronized void showBuilderProjects(String builderName) {
        boolean found = false;
        for (Project project : projects.values()) {
            if (builderName.equals(project.getBuilderName())) {
                System.out.println(project);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects assigned.");
        }
    }
    public synchronized void showBuilderTasks(String builderName) {
        boolean found = false;
        for (Project project : projects.values()) {
            for (Task task : project.getTasks()) {
                if (builderName.equals(task.getAssignedBuilder())) {
                    System.out.println("Project: " + project.getProjectId());
                    System.out.println(task);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No tasks assigned.");
        }
    }
    public synchronized void updateTaskStatus(String projectId, String taskId, String builderName) {
        Project project = projects.get(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        for (Task task : project.getTasks()) {
            if (task.getTaskId().equals(taskId) && task.getAssignedBuilder().equals(builderName)) {
                task.setStatus(TASK_STATUS.Completed);
                System.out.println("Task marked as completed!");
                return;
            }
        }
        System.out.println("Task not found or not assigned to you.");
    }
    public synchronized void showClientProjects(String clientId) {
        boolean found = false;
        for (Project project : projects.values()) {
            if (clientId.equals(project.getClientId())) {
                System.out.println(project);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found for this client.");
        }
    }
}
