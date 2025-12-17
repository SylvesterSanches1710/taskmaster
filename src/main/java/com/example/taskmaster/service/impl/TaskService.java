package com.example.taskmaster.service.impl;

import org.springframework.data.domain.Page;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.entity.Task;

public interface TaskService {
    Task createTask(Long projectId, TaskDTO dto, String currentUserEmail);

    Page<Task> getTasks(Long projectId, String status, int page, int size, String currentUserEmail);

    Task updateTask(Long taskId, TaskDTO dto, String currentUserEmail);

    void deleteTask(Long taskId, String currentUserEmail);
}
