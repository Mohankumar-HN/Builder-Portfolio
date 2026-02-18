package com.zeta;

import com.zeta.Dao.UserDao;
import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.ROLE_TYPE;
import com.zeta.entity.TASK_STATUS;
import com.zeta.entity.User;
import com.zeta.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestProjectService {

    ProjectService projectService;
    String projectId;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
        projectService.getProjects().clear();
        projectService.createProject( "Skyrise", "6 storage buildings", "2022-09-09",
                "2026-09-09", "C1", "null",PROJECT_STATUS.UPCOMING);
        projectId = projectService.getProjects().keySet().iterator().next();
        UserDao.users.clear();
        UserDao.users.add(new User("U2", "BuilderA", "Password@1", ROLE_TYPE.BUILDER));

    }

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
    void testShowProjects_whenNoProjectFound() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        projectService.showProjects("INVALID_PM");
        String output = outputStream.toString();
        assertEquals("", output.trim());
    }

    @Test
    void testAssignBuilderProjectNotFound() {
        projectService.assignBuilder("INVALID", "BuilderA");
    }

    @Test
    void testCreateTask() {
        projectService.createTask(projectId, "Foundation Work",
                "Start digging", "BuilderA");
        assertEquals(1,
                projectService.getProjects().get(projectId).getTasks().size());
    }

    @Test
    void testCreateTaskProjectNotFound() {
        projectService.createTask( "INVALID", "Task",
                "Desc", "BuilderA");
    }
    @Test
    void testUpdateTaskStatusSuccess() {
        projectService.createTask(projectId, "Plumbing", "Install pipes", "BuilderA");
        String taskId = projectService.getProjects().get(projectId).getTasks().get(0).getTaskId();
        projectService.updateTaskStatus(projectId, taskId, "BuilderA", TASK_STATUS.COMPLETED);
        assertEquals(TASK_STATUS.COMPLETED,
                projectService.getProjects().get(projectId).getTasks().get(0).getStatus());
    }
    @Test
    void testUpdateTaskStatusWrongBuilder() {
        projectService.createTask(projectId,  "Electrical",
                "Wiring", "BuilderA");

        String taskId = projectService.getProjects().get(projectId).getTasks().get(0).getTaskId();

        projectService.updateTaskStatus(projectId, taskId, "WrongBuilder",TASK_STATUS.COMPLETED);

        assertNotEquals(TASK_STATUS.COMPLETED,
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
        projectService.createTask(projectId, "Roof Work", "Concrete", "BuilderA");

        projectService.showBuilderTasks("BuilderA");
    }

    @Test
    void testShowAllTasks() {
        projectService.createTask(projectId, "Painting", "Wall paint", "BuilderA");
        projectService.showAllTasks();
    }
}
