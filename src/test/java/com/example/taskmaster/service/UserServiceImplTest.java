package com.example.taskmaster.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.taskmaster.entity.Role;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.repository.UserRepository;
import com.example.taskmaster.security.JwtUtil;
import com.example.taskmaster.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_success() {

        String name = "Sylvester";
        String email = "test@mail.com";
        String rawPassword = "password123";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn("hashedPassword");

        User savedUser = new User();
        savedUser.setName(name);
        savedUser.setEmail(email);
        savedUser.setPassword("hashedPassword");
        savedUser.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(name, email, rawPassword);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }

}
