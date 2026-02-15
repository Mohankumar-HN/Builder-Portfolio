
package com.zeta.service;
import com.zeta.model.PROJECT_STATUS;
import com.zeta.model.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectService {
    private Map<String,Project> projects = new HashMap<>();
    public synchronized void createProject(String id, String name, String description,
                                           String start, String end, String clientId, String managerId) {

        Project project = new Project(id, name, description, start, end, clientId, managerId, PROJECT_STATUS.UPCOMING);

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



}
