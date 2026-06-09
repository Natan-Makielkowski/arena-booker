package com.example.arena_booker.dto;

import com.example.arena_booker.model.Sector;

import java.time.LocalDateTime;

public record ReservationRequestDto (Sector sector, LocalDateTime startTime, LocalDateTime endTime) {
    public ReservationRequestDto {
        if (startTime != null && endTime != null && !startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Reservation has to start before it ends.");
        }}

}
