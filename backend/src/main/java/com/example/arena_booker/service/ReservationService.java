package com.example.arena_booker.service;

import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReservationService {
    ReservationRepository reservationRepository;

    public List<ReservationResponseDto> getAllReservations() {

        return reservationRepository.findAll().stream().map(this::mapReservationToReservationDto).toList();
    }

    private ReservationResponseDto mapReservationToReservationDto (Reservation reservation) {
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(
                reservation.getId(),
                reservation.getSector().toString(),
                reservation.getStartTime(),
                reservation.getEndTime());
        return reservationResponseDto;
    }


}
