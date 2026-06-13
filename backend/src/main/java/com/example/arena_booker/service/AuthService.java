package com.example.arena_booker.service;

import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String rawPassword) {
        Optional<AppUser> userOptional = appUserRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return false;
        }

        AppUser user = userOptional.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}