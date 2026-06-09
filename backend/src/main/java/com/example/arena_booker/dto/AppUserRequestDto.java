package com.example.arena_booker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserRequestDto (@NotBlank @Size(max = 20) String username,@NotBlank @Size(min = 6, max = 32) String password) {
}
