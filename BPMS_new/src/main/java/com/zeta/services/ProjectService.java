
package com.zeta.services;
import com.zeta.entity.PROJECT_STATUS;
import com.zeta.entity.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectService {
    final Map<String,Project> projects = new HashMap<>();
    public synchronized void createProject(String id, String name, String description,
                                           String start, String end, String clientId,PROJECT_STATUS status) {

        Project project = new Project(id, name, description, start, end, clientId,status);
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
        }else{
            System.out.println("project not found");
        }

    }

    public  synchronized void assignBuilder(String id,String name){
        Project project=projects.get(id);
        if(project!=null){
            project.setBuilderName(name);
            System.out.println("Builder assigned successfully");
        }else{
            System.out.println("project not found");
        }
    }

}
