package com.example.taskmaster.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.entity.Project;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.repository.ProjectRepository;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;
    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public TaskServiceImpl(TaskRepository taskRepo, ProjectRepository projectRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Task createTask(Long projectId, TaskDTO dto, String currentUserEmail) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getOwner().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        Task task = new Task();
        task.setTitle(dto.title);
        task.setDescription(dto.description);
        task.setProject(project);
        if (dto.assigneeId != null) {
            User assignee = userRepo.findById(dto.assigneeId)
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);
        }
        return taskRepo.save(task);
    }

    @Override
    public Page<Task> getTasks(Long projectId, String status, int page, int size, String currentUserEmail) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getOwner().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Unauthorized");

        }

        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return taskRepo.findByProjectIdAndStatus(projectId, TaskStatus.valueOf(status), pageable);
        }
        return taskRepo.findByProjectId(projectId, pageable);
    }

    @Override
    public Task updateTask(Long taskId, TaskDTO dto, String currentUserEmail) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        Project project = task.getProject();

        boolean isOwner = project.getOwner().getEmail().equals(currentUserEmail);

        boolean isAssignee = task.getAssignee() != null &&
                task.getAssignee().getEmail().equals(currentUserEmail);

        if (!isOwner && !isAssignee) {
            throw new RuntimeException("Unauthorized");
        }

        if (isAssignee && !isOwner) {
            if (dto.title != null || dto.description != null || dto.assigneeId != null) {
                throw new RuntimeException("Assignee can only update task status");
            }
        }

        if (dto.title != null) {
            task.setTitle(dto.title);
        }

        if (dto.description != null) {
            task.setDescription(dto.description);
        }

        if (dto.status != null) {
            task.setStatus(TaskStatus.valueOf(dto.status));
        }

        if (dto.assigneeId != null) {
            User assignee = userRepo.findById(dto.assigneeId)
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);

        }
        return taskRepo.save(task);
    }

    @Override
    public void deleteTask(Long taskId, String currentUserEmail) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        Project project = task.getProject();
        if (!project.getOwner().getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Unauthorized");
        }

        taskRepo.delete(task);
    }
}