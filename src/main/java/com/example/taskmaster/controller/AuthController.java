package com.example.taskmaster.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmaster.dto.LoginRequest;
import com.example.taskmaster.dto.RegisterRequest;
import com.example.taskmaster.service.impl.UserService;
import com.example.taskmaster.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return service.registerUser(
                request.name,
                request.email,
                request.password);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return service.login(
                request.email,
                request.password);
    }
}
