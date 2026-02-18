package com.zeta.Dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta.console.App;
import com.zeta.entity.Task;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TaskDao {
	private static final String FILE_NAME = System.getProperty("user.dir") + "/tasks.json";
	private final ObjectMapper objectMapper = new ObjectMapper();
	static final Logger logger= Logger.getLogger(TaskDao.class.getName());
	public Map<String, List<Task>> loadTasks() {
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			logger.info("tasks.json does not exist yet.");
			return new HashMap<>();
		}
		try {
			return objectMapper.readValue(file, new TypeReference<Map<String, List<Task>>>() {});
		} catch (IOException e) {
			logger.info("Error loading tasks: " + e.getMessage());
			return new HashMap<>();
		}
	}

	public void saveTasks(Map<String, List<Task>> tasksByProject) {
		try {
			File file = new File(FILE_NAME);
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) parent.mkdirs();
			if (!file.exists()) file.createNewFile();
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tasksByProject);
		} catch (IOException e) {
			logger.info("Error saving tasks: " + e.getMessage());
		}
	}
}
