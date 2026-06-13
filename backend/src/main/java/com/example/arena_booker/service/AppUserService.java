package com.example.arena_booker.service;

import com.example.arena_booker.Exception.ResourceNotFoundException;
import com.example.arena_booker.Exception.UserConflictException;
import com.example.arena_booker.dto.AppUserRequestDto;
import com.example.arena_booker.dto.AppUserResponseDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUserResponseDto> getUsers() {
        return appUserRepository.findAll().stream().map(this::mapAppUserToDto).toList();
    }

    public AppUserResponseDto addUser(AppUserRequestDto appUserRequestDto) {
        if(appUserRepository.existsByUsername(appUserRequestDto.username())){
            throw new UserConflictException("This username is taken. ");
        }
        AppUser newAppUser = new AppUser(appUserRequestDto.username(), passwordEncoder.encode(appUserRequestDto.password()), "USER");
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
        AppUser newAppUser = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such user was found"));
        newAppUser.setRole(role);
        appUserRepository.save(newAppUser);
    }

    public void deleteUser(Integer id) {
        appUserRepository.deleteById(id);
    }







}
