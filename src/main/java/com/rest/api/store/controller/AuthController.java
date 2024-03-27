package com.rest.api.store.controller;

import com.rest.api.store.dto.AuthDTO;
import com.rest.api.store.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping(path = "/customer")
    public String checkAuthentication(Authentication authentication) {
        return "An Admin or Customer can hit this rout. Employees name is " + authentication.getName();
    }

    @GetMapping(path = "/authenticated")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String getAuthenticated(Authentication authentication) {
        return "Admin name is " + authentication.getName();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDTO authDTO) {
        return ResponseEntity
                .status(CREATED)
                .body(authService.register(authDTO));
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(
            @Valid @RequestBody AuthDTO authDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ResponseEntity<>(authService.login(authDTO, request, response), OK);
    }
}
