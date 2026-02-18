package com.zeta.Dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zeta.entity.Project;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ProjectDao {
    private static final String FILE_NAME = System.getProperty("user.dir") + "/projects.json";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    static final Logger logger = Logger.getLogger(ProjectDao.class.getName());
    public Map<String, Project> projects = new HashMap<>();

    public Map<String, Project> loadProjects() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            logger.info("projects.json does not exist yet.");
            return new HashMap<>();
        }
        if (file.length() == 0) {
            logger.info("projects.json is empty; nothing to load.");
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(file,
                    new TypeReference<Map<String, Project>>() {
                    });
        } catch (IOException ioException) {
            logger.info("Error loading projects : " + ioException.getMessage());
            return new HashMap<>();
        }
    }

    public void saveProjects(Map<String, Project> projects) {
        try {
            if (projects == null || projects.isEmpty()) {
                File existing = new File(FILE_NAME);
                if (existing.exists()) {
                    logger.info("Projects map empty" + existing.getAbsolutePath());
                    return;
                }
            }
            File file = new File(FILE_NAME);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            File tmp = new File(file.getAbsolutePath() + ".tmp");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(tmp, projects);
            try {
                java.nio.file.Files.move(tmp.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (Exception ex) {

                java.nio.file.Files.move(tmp.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ioException) {
            logger.info("Error saving data: " + ioException.getMessage());
        }

    }
}

