package com.example.arena_booker.config;


import com.example.arena_booker.dto.AppUserRequestDto;
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
        if (appUserRepository.count() == 0) {
            appUserService.addUser(new AppUserRequestDto("admin", "admin123"));




        }
    }
}