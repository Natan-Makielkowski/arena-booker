package com.example.arena_booker.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(LocalDateTime timestamp, Integer status, String error,String message) {
}
