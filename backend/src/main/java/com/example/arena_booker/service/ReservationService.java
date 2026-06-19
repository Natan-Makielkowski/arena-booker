package com.example.arena_booker.service;

import com.example.arena_booker.Exception.ReservationConflictException;
import com.example.arena_booker.Exception.ResourceNotFoundException;
import com.example.arena_booker.dto.ReservationRequestDto;
import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.model.Sector;
import com.example.arena_booker.repository.AppUserRepository;
import com.example.arena_booker.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AppUserRepository appUserRepository;

    public ReservationService(ReservationRepository reservationRepository, AppUserRepository appUserRepository) {
        this.reservationRepository = reservationRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ReservationResponseDto> getUsersReservations(String username){
        return reservationRepository.findAllByAppUserUsername(username).stream()
                .map(this::mapReservationToReservationDto)
                .toList();
    }



    private ReservationResponseDto mapReservationToReservationDto (Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getSector(),
                reservation.getStartTime(),
                reservation.getEndTime());
    }

    @Transactional
    public ReservationResponseDto createReservation(@NonNull ReservationRequestDto reservationRequestDto, String username){
        if(isTaken(reservationRequestDto.sector(), reservationRequestDto.startTime(), reservationRequestDto.endTime())) {
            throw new ReservationConflictException("Sector is already in use.");
        }

        AppUser currentUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Reservation reservation = new Reservation(reservationRequestDto.sector(), reservationRequestDto.startTime(), reservationRequestDto.endTime());
        reservation.setAppUser(currentUser);
        reservationRepository.save(reservation);
        return mapReservationToReservationDto(reservation);

    }

    public void updateReservation(@NonNull Integer id, @NonNull ReservationRequestDto reservationRequestDto) {
        if(isTakenByAnother(id, reservationRequestDto.sector(), reservationRequestDto.startTime(), reservationRequestDto.endTime())){
            throw new ReservationConflictException("Sector is already in use.");
        }

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setSector(reservationRequestDto.sector());
        reservation.setStartTime(reservationRequestDto.startTime());
        reservation.setEndTime(reservationRequestDto.endTime());
        reservationRepository.save(reservation);
    }

    public void deleteReservation(@NonNull Integer id){
        reservationRepository.deleteById(id);
    }

    public boolean isTaken(Sector sector, LocalDateTime startTime, LocalDateTime endTime) {
        return !reservationRepository.overlappingReservations(sector, startTime, endTime).isEmpty();
    }

    public boolean isTakenByAnother(Integer currentReservationId, Sector sector, LocalDateTime startTime, LocalDateTime endTime) {
        return !reservationRepository.overlappingReservationsExcludingCurrent(currentReservationId, sector, startTime, endTime).isEmpty();
    }


    }


