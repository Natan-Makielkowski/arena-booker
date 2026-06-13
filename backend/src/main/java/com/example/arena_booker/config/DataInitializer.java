package com.example.arena_booker.config;


import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.repository.AppUserRepository;
import com.example.arena_booker.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    public DataInitializer(AppUserRepository appUserRepository,  AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!appUserRepository.existsByUsername("admin")) {
            AppUserRequestDto adminDto = new AppUserRequestDto("admin", "admin123");
            appUserService.addUser(adminDto);

            appUserService.changeRole(1, "ADMIN");
    }
}}
