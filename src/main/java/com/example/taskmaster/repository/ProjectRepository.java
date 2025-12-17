package com.example.taskmaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmaster.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long ownerId);
}
