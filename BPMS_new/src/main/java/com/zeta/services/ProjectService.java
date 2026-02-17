
package com.zeta.services;
import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.Project;
import com.zeta.entity.TASK_STATUS;
import com.zeta.entity.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zeta.Dao.ProjectDao;
import com.zeta.Dao.TaskDao;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class ProjectService {

    private final ProjectDao projectDao = new ProjectDao();
    private final TaskDao taskDao = new TaskDao();
    private Map<String, Project> projects;
    private Map<String, List<Task>> tasksByProject;

    private static int projectCounter = 1;
    private String generateProjectId() {
        return "P" + (projectCounter++);
    }
    public ProjectService() {
        projects = projectDao.loadProjects();
        tasksByProject = taskDao.loadTasks();
        // populate project tasks from tasksByProject
        for (Map.Entry<String, List<Task>> entry : tasksByProject.entrySet()) {
            String pid = entry.getKey();
            List<Task> ts = entry.getValue();
            Project p = projects.get(pid);
            if (p != null && ts != null) {
                p.getTasks().clear();
                p.getTasks().addAll(ts);
            }
        }
        projectCounter = projects.size() + 1;
    }
    public synchronized void createProject(String id, String name, String description,
                                           String start, String end, String clientId,PROJECT_STATUS status) {
        id = generateProjectId();
        try {
            LocalDate startDate = LocalDate.parse(start);  // format: yyyy-MM-dd
            LocalDate endDate = LocalDate.parse(end);
            if (endDate.isBefore(LocalDate.now())) {
                System.out.println("End date cannot be in the past!");
                return;
            }
            if (endDate.isBefore(startDate)) {
                System.out.println("End date cannot be before start date!");
                return;
            }
            Project project = new Project(id, name, description, startDate, endDate, clientId, status);
            projects.put(id, project);
            projectDao.saveProjects(projects);
            System.out.println("Project Created Successfully!");

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use yyyy-MM-dd");
        }
    }

    public synchronized void updateStatus(String projectId, PROJECT_STATUS newStatus) {
        Project project = projects.get(projectId);
        if (project != null) {
            project.setStatus(newStatus);
            System.out.println("Status Updated Successfully!");
            projectDao.saveProjects(projects);
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
            projectDao.saveProjects(projects);
            // also remove tasks for this project
            if (tasksByProject != null && tasksByProject.containsKey(id)) {
                tasksByProject.remove(id);
                taskDao.saveTasks(tasksByProject);
            }
        }else{
            System.out.println("project not found");
        }
    }
    public  synchronized void assignBuilder(String id,String name){
        Project project=projects.get(id);
        if(project!=null){
            project.setBuilderName(name);
            System.out.println("Builder assigned successfully");
            projectDao.saveProjects(projects);
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
            System.out.println("Task created successfully!");
            projectDao.saveProjects(projects);
            
            tasksByProject.computeIfAbsent(projectId, k -> new java.util.ArrayList<>()).add(task);
            taskDao.saveTasks(tasksByProject);
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
                projectDao.saveProjects(projects);
                // update tasks.json
                List<Task> ts = tasksByProject.get(projectId);
                if (ts != null) {
                    for (Task t : ts) {
                        if (t.getTaskId().equals(taskId)) {
                            t.setStatus(TASK_STATUS.Completed);
                            break;
                        }
                    }
                    taskDao.saveTasks(tasksByProject);
                }
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
    public Map<String,Project> getProjects(){
        return projects;
    }
}
