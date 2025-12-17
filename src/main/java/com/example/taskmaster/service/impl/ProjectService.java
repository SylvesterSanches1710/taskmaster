package com.example.taskmaster.service.impl;

import java.util.List;

import com.example.taskmaster.dto.ProjectDTO;
import com.example.taskmaster.entity.Project;

public interface ProjectService {
    Project create(ProjectDTO dto, String ownerEmail);

    List<Project> getMyProjects(String ownerEmail);
}
