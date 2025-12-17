package com.example.taskmaster.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmaster.entity.Role;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.repository.UserRepository;
import com.example.taskmaster.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder, JwtUtil jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    // @Override
    // public User register(User user) {
    // return repo.save(user);
    // }

    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public User registerUser(String name, String email, String rawPassword) {
        if (repo.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(rawPassword));
        user.setRole(Role.USER);

        return repo.save(user);
    }

    @Override
    public String login(String email, String rawPassword) {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!encoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwt.generateToken(user.getEmail(), user.getRole().name());
    }

}
