package com.example.arena_booker.service;

import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.dto.AppUserResponseDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUserResponseDto> getUsers() {
        return appUserRepository.findAll().stream().map(this::mapAppUserToDto).toList();
    }

    public AppUserResponseDto addUser(AppUserRequestDto appUserRequestDto) {
        AppUser newAppUser = new AppUser(appUserRequestDto.username(),"{noop}" + appUserRequestDto.password(), "USER");
        appUserRepository.save(newAppUser);
        return mapAppUserToDto(newAppUser);
    }



    public AppUserResponseDto mapAppUserToDto(AppUser appUser) {
        return new AppUserResponseDto(
                appUser.getUsername(),
                appUser.getRole()
        );
    }

    public void changeRole(Integer id, String role) {
        AppUser newAppUser = appUserRepository.findById(id).orElseThrow();
        newAppUser.setRole(role);
        appUserRepository.save(newAppUser);
    }

    public void deleteUser(Integer id) {
        appUserRepository.deleteById(id);
    }







}
