package com.example.taskmaster.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.taskmaster.entity.Role;
import com.example.taskmaster.entity.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setPassword("123");
        user.setRole(Role.USER);

        userRepository.save(user);

        User found = userRepository.findByEmail("test@mail.com");

        assertNotNull(found);
        assertEquals("test@mail.com", found.getEmail());
    }

    @Test
    void shouldCheckIfEmailExists() {
        User user = new User();
        user.setName("Test");
        user.setEmail("exists@mail.com");
        user.setPassword("123");
        user.setRole(Role.USER);

        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("exists@mail.com");

        assertTrue(exists);
    }
}
