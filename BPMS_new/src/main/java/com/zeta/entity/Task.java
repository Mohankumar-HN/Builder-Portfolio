package com.zeta.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    @JsonProperty("taskId")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("assignedBuilder")
    private String assignedBuilder;
    @JsonProperty("status")
    private TASK_STATUS status;
    public Task() {}
    public Task(String taskId, String taskName,
                String description, String assignedBuilder) {
        this.id = taskId;
        this.name = taskName;
        this.description = description;
        this.assignedBuilder = assignedBuilder;
        status = TASK_STATUS.UPCOMING;
    }
    public String getAssignedBuilder() {
        return assignedBuilder;
    }
    public void setAssignedBuilder(String assignedBuilder) {
        this.assignedBuilder = assignedBuilder;
    }
    public String getTaskId() {
        return id;
    }
    public void setTaskId(String taskId) {
        this.id = taskId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public TASK_STATUS getStatus() {
        return status;
    }
    public void setStatus(TASK_STATUS status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Task Details " +
                "\ntaskId :" + id +
                "\ntaskName :" + name +
                "\nassignedBuilder :" + assignedBuilder +
                "\nstatus :" + status;
    }
}