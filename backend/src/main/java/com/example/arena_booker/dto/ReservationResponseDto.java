package com.example.arena_booker.dto;

import java.time.LocalDateTime;

public record ReservationResponseDto (Integer id, String Sector, LocalDateTime startTime, LocalDateTime endTime) {
}
