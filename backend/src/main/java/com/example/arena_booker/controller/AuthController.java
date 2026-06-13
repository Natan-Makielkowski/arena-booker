package com.example.arena_booker.controller;

import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUserRequestDto loginRequest) {
        Optional<AppUser> userOptional = appUserRepository.findByUsername(loginRequest.username());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password. ");
        }

        AppUser user = userOptional.get();

        if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {

            return ResponseEntity.ok().body("Token: ");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password. ");
        }
    }
}