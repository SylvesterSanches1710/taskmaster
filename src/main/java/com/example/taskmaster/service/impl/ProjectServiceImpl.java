package com.example.taskmaster.service.impl;

import com.example.taskmaster.dto.ProjectDTO;
import com.example.taskmaster.entity.Project;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.repository.ProjectRepository;
import com.example.taskmaster.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public ProjectServiceImpl(ProjectRepository projectRepo, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Project create(ProjectDTO dto, String ownerEmail) {

        User owner = userRepo.findByEmail(ownerEmail);
        if (owner == null) {
            throw new RuntimeException("Owner not found");
        }

        Project project = new Project();
        project.setName(dto.name);
        project.setDescription(dto.description);
        project.setOwner(owner);

        return projectRepo.save(project);
    }

    @Override
    public List<Project> getMyProjects(String ownerEmail) {

        User owner = userRepo.findByEmail(ownerEmail);
        if (owner == null) {
            throw new RuntimeException("User not found");
        }

        return projectRepo.findByOwnerId(owner.getId());
    }
}
