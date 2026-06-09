package com.example.arena_booker.service;

import com.example.arena_booker.Exception.ReservationConflictException;
import com.example.arena_booker.Exception.ResourceNotFoundException;
import com.example.arena_booker.dto.ReservationRequestDto;
import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.model.Sector;
import com.example.arena_booker.repository.ReservationRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReservationService {
    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> getAllReservations() throws Exception{
        if(reservationRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("Reservations not found");
        }
        return reservationRepository.findAll().stream().map(this::mapReservationToReservationDto).toList();

    }

    private ReservationResponseDto mapReservationToReservationDto (Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getSector(),
                reservation.getStartTime(),
                reservation.getEndTime());
    }

    public ReservationResponseDto createReservation(@NonNull ReservationRequestDto reservationRequestDto) throws Exception {
        if(isTaken(reservationRequestDto.sector(), reservationRequestDto.startTime(), reservationRequestDto.endTime())) {
            throw new ReservationConflictException("Sector is already in use.");
        }

        Reservation reservation = new Reservation(reservationRequestDto.sector(), reservationRequestDto.startTime(), reservationRequestDto.endTime());
        reservationRepository.save(reservation);
        return mapReservationToReservationDto(reservation);

    }

    public boolean isTaken(Sector sector, LocalDateTime startTime, LocalDateTime endTime) {
        return !reservationRepository.overlappingReservations(sector, startTime, endTime).isEmpty();
    }
    }


