package com.zeta.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String builderName;
    private List<Task> tasks = new ArrayList<>();

    public Project(String projectId, String name, String description,
                   String startDate, String endDate,
                   String clientId,PROJECT_STATUS status) {

        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
//        this.projectManagerId = projectManagerId;
        this.status = status;
    }
    public Project(){};
    public String getProjectId() {
        return projectId;
    }
    public String getClientId() { return clientId; }

    public PROJECT_STATUS getStatus() {
        return status;
    }

    public void setStatus(PROJECT_STATUS status) {
        this.status = status;
    }

    public void setBuilderName(String builderName) {
        this.builderName = builderName;
    }


    public List<Task> getTasks() {
        return tasks;
    }
    public String getBuilderName() {
        return builderName;
    }


    @Override
    public String toString() {
        return "Project ID: " + projectId +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nClient ID: " + clientId +
                "\nProject Manager ID: " + projectManagerId +
                "\nBuilder ID: " + builderId +
                "\nStatus: " + status ;
    }


}
