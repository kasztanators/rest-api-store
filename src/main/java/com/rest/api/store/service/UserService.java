package com.rest.api.store.service;

import com.rest.api.store.entity.User;
import com.rest.api.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        // Implement your registration logic here, including password hashing
        // Ensure email uniqueness
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        // Implement your login logic here
        // Validate email and password
        // Return the user if login is successful
        // Otherwise, throw an exception or return null
        return new User();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
