package com.zeta;

import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.TASK_STATUS;
import com.zeta.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProjectService {

    ProjectService projectService;
    String projectId;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
        projectService.createProject(null, "Skyrise", "6 storage buildings", "12-02-2026",
                "12-09-2026", "C1", PROJECT_STATUS.UPCOMING);
        projectId = projectService.getProjects().keySet().iterator().next();
    }
//checking git conflicts
    @Test
    void testCreateProject() {
        assertEquals(1, projectService.getProjects().size());
    }

    @Test
    void testUpdateStatus() {
        projectService.updateStatus(projectId, PROJECT_STATUS.COMPLETED);
        assertEquals(PROJECT_STATUS.COMPLETED,
                projectService.getProjects().get(projectId).getStatus());
    }

    @Test
    void testUpdateStatusProjectNotFound() {
        projectService.updateStatus("INVALID", PROJECT_STATUS.COMPLETED);
    }

    @Test
    void testAssignBuilder() {
        projectService.assignBuilder(projectId, "BuilderA");
        assertEquals("BuilderA",
                projectService.getProjects().get(projectId).getBuilderName());
    }

    @Test
    void testAssignBuilderProjectNotFound() {
        projectService.assignBuilder("INVALID", "BuilderA");
    }

    @Test
    void testCreateTask() {
        projectService.createTask(projectId, null, "Foundation Work",
                "Start digging", "BuilderA");
        assertEquals(1,
                projectService.getProjects().get(projectId).getTasks().size());
    }

    @Test
    void testCreateTaskProjectNotFound() {
        projectService.createTask("INVALID", null, "Task",
                "Desc", "BuilderA");
    }
    @Test
    void testUpdateTaskStatusSuccess() {
        projectService.createTask(projectId, null,
                "Plumbing",
                "Install pipes",
                "BuilderA");

        String taskId = projectService.getProjects().get(projectId).getTasks().get(0).getTaskId();
        projectService.updateTaskStatus(projectId, taskId, "BuilderA");
        assertEquals(TASK_STATUS.Completed,
                projectService.getProjects().get(projectId).getTasks().get(0).getStatus());
    }
    @Test
    void testUpdateTaskStatusWrongBuilder() {
        projectService.createTask(projectId, null, "Electrical",
                "Wiring", "BuilderA");

        String taskId = projectService.getProjects().get(projectId).getTasks().get(0).getTaskId();

        projectService.updateTaskStatus(projectId, taskId, "WrongBuilder");

        assertNotEquals(TASK_STATUS.Completed,
                projectService.getProjects().get(projectId).getTasks().get(0).getStatus());
    }
    @Test
    void testDeleteProject() {
        projectService.deleteProject(projectId);
        assertTrue(projectService.getProjects().isEmpty());
    }

    @Test
    void testDeleteProjectNotFound() {
        projectService.deleteProject("INVALID");
    }

    @Test
    void testShowClientProjects() {
        projectService.showClientProjects("C1");
    }

    @Test
    void testShowBuilderProjects() {
        projectService.assignBuilder(projectId, "BuilderA");
        projectService.showBuilderProjects("BuilderA");
    }

    @Test
    void testShowBuilderTasks() {
        projectService.createTask(projectId, null, "Roof Work", "Concrete", "BuilderA");

        projectService.showBuilderTasks("BuilderA");
    }

    @Test
    void testShowAllTasks() {
        projectService.createTask(projectId, null, "Painting", "Wall paint", "BuilderA");

        projectService.showAllTasks();
    }
}
