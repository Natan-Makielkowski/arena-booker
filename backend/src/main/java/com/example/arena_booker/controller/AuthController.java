package com.example.arena_booker.controller;

import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.service.AuthService;
import com.example.arena_booker.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;


    public AuthController(AuthService authService,  JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUserRequestDto loginRequest) {
        boolean isAuthorized = authService.authenticate(
                loginRequest.username(),
                loginRequest.password()
        );

        if (isAuthorized) {
            String jwtToken = jwtService.generateToken(loginRequest.username());
            return ResponseEntity.ok(Map.of("token", jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

}