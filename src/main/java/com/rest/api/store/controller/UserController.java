package com.rest.api.store.controller;

import com.rest.api.store.dto.LoginRequest;
import com.rest.api.store.entity.User;
import com.rest.api.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Implement registration endpoint
        // Return appropriate HTTP status codes and response
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest request) {
        // Implement login endpoint
        // Return appropriate HTTP status codes and response
        return null;
    }
}
