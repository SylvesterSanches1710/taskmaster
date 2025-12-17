package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.service.impl.TaskService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public Task createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskDTO dto,
            @AuthenticationPrincipal String email) {

        return service.createTask(projectId, dto, email);
    }

    @GetMapping
    public Page<Task> getTasks(
            @PathVariable Long projectId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal String email) {

        return service.getTasks(projectId, status, page, size, email);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskDTO dto,
            @AuthenticationPrincipal String email) {

        return service.updateTask(taskId, dto, email);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long projectId,
            @PathVariable Long taskId,
            @AuthenticationPrincipal String email) {

        service.deleteTask(taskId, email);
    }
}
