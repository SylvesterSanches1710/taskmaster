package com.example.taskmaster.service.impl;

import com.example.taskmaster.entity.User;

public interface UserService {

    User findByEmail(String email);

    User registerUser(String name, String email, String password);

    String login(String email, String password);
}
