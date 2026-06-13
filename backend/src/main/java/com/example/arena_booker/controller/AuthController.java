package com.example.arena_booker.controller;

import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUserRequestDto loginRequest) {
        boolean isAuthorized = authService.authenticate(
                loginRequest.username(),
                loginRequest.password()
        );

        if (isAuthorized) {

            return ResponseEntity.ok(Map.of("token key", "token value"));
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password. ");
        }
    }
}