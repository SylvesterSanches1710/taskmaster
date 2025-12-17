package com.example.taskmaster.dto;

import jakarta.validation.constraints.NotBlank;

public class ProjectDTO {
    public Long id;

    @NotBlank
    public String name;
    public String description;
}
