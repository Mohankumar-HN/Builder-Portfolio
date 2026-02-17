package com.zeta.entity;

public class Task {

    private String id;
    private String name;
    private String description;
    private String assignedBuilder;
    private TASK_STATUS status;   // PENDING / COMPLETED


    public Task(String taskId, String taskName,
                String description, String assignedBuilder) {
        this.id = taskId;
        this.name = taskName;
        this.description = description;
        this.assignedBuilder = assignedBuilder;
        status = TASK_STATUS.Upcoming;
    }

    public String getAssignedBuilder() {
        return assignedBuilder;
    }

    public String getTaskId() {
        return id;
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
                "\ntaskId :" + id  +
                "\ntaskName :" + name  +
                "\nassignedBuilder :" + assignedBuilder  +
                "\nstatus :" + status;
    }
}
