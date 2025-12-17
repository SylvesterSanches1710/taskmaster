package com.example.taskmaster.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmaster.dto.ProjectDTO;
import com.example.taskmaster.entity.Project;
import com.example.taskmaster.service.impl.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public Project create(@Valid @RequestBody ProjectDTO dto, Principal principal) {
        return service.create(dto, principal.getName());
    }

    @GetMapping
    public List<Project> getMyProjects(Principal principal) {
        return service.getMyProjects(principal.getName());
    }
}
