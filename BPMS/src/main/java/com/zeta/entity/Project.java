package com.zeta.entity;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String projectId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String clientId;
    private String projectManagerId;
    private String builderId;
    private PROJECT_STATUS status;

//    private List<Task> tasks = new ArrayList<>();

    public Project(String projectId, String name, String description,
                   String startDate, String endDate,
                   String clientId, String projectManagerId,PROJECT_STATUS status) {

        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
        this.projectManagerId = projectManagerId;
        this.status = "UPCOMING";
    }

    public String getProjectId() {
        return projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBuilderName(String builderName) {
        this.builderId = builderName;
    }

//    public List<Task> getTasks() {
//        return tasks;
//    }

}
