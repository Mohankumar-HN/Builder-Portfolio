package com.zeta;

import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.Project;
import com.zeta.entity.TASK_STATUS;
import com.zeta.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestProjectService {

    ProjectService projectService;
    @BeforeEach
    void setUp(){
        projectService=new ProjectService();
        projectService.createProject("P6","skyrise","6 storage buildings","12-02-2026","12-09-2026",
                "C1", PROJECT_STATUS.UPCOMING);

    }
    @Test
    public void testCreateProject(){
        assertEquals(1,projectService.getProjects().size());
    }

    @Test
    public void testAssignBuilder(){
        String projectId = projectService.projects.keySet().iterator().next();
        projectService.assignBuilder(projectId,"BuilderA");
        assertEquals("BuilderA",
                projectService.projects.get(projectId).getBuilderName());
        assertThrows(ExpectedExceptionType.class, () -> {
            // Code that should throw the exception
        });

    }
    @Test
    public void testCreateTask() {
        String projectId = projectService.projects.keySet().iterator().next();
        projectService.createTask(projectId, null, "Foundation Work",
                "Start digging", "BuilderA");
        assertEquals(1,
                projectService.projects.get(projectId).getTasks().size());

    }
//    @Test
//    void testUpdateTaskStatus() {
//        String projectId = projectService.projects.keySet().iterator().next();
//
//        String taskId = projectService.projects
//                .get(projectId).getTasks().get(0).getTaskId();
//
//        projectService.updateTaskStatus(projectId, taskId, "BuilderA");
//        assertEquals(TASK_STATUS.Completed,
//                projectService.projects.get(projectId)
//                        .getTasks().get(0).getStatus());
//    }






}
