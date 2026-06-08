package com.example.arena_booker.controller;

import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.dto.AppUserResponseDto;
import com.example.arena_booker.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    AppUserService appUserService;
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUserResponseDto> getUsers() {
        return appUserService.getUsers();
    }

    @PostMapping
    public AppUserResponseDto addUser(@RequestBody AppUserRequestDto appUserRequestDto) {
        return appUserService.addUser(appUserRequestDto);
    }



}
