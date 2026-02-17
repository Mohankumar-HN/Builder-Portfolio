package com.zeta;

import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.Project;
import com.zeta.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.zeta.services.ProjectService.projects;
import static junit.framework.Assert.assertEquals;


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
        assertEquals(1,projects.size());
    }

    @Test
    public void testAssignBuilder(){
        String projectId = projectService.projects.keySet().iterator().next();
        projectService.assignBuilder(projectId,"BuilderA");
        assertEquals("BuilderA",
                projectService.projects.get(projectId).getBuilderName());
    }


    @Test
    public void testCreateTask() {
        String projectId = projectService.projects.keySet().iterator().next();
        projectService.createTask(projectId, null, "Foundation Work",
                "Start digging", "BuilderA");
        assertEquals(1,
                projectService.projects.get(projectId).getTasks().size());
        assertEquals("P1",projectService.projects.get(projectId).getProjectId());
    }






}
