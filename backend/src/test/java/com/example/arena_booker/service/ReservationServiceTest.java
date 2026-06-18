package com.example.arena_booker.service;

import com.example.arena_booker.Exception.ReservationConflictException;
import com.example.arena_booker.dto.ReservationRequestDto;
import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.AppUser;
import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.model.Sector;
import com.example.arena_booker.repository.AppUserRepository;
import com.example.arena_booker.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private ReservationService reservationService;

    private AppUser sampleUser;
    private ReservationRequestDto sampleRequest;

    @BeforeEach
    void setUp() {
        sampleUser = new AppUser("admin", "hashed_password", "USER");
        sampleRequest = new ReservationRequestDto(
                Sector.SECTOR_A,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)
        );
    }

    @Test
    void createReservation_ShouldSuccess_WhenSectorIsFreeAndUserExists() {
        when(reservationRepository.overlappingReservations(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(appUserRepository.findByUsername("admin"))
                .thenReturn(Optional.of(sampleUser));

        ReservationResponseDto response = reservationService.createReservation(sampleRequest, "admin");

        assertNotNull(response);
        assertEquals(Sector.SECTOR_A, response.sector());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void createReservation_ShouldThrowException_WhenSectorIsAlreadyTaken() {
        when(reservationRepository.overlappingReservations(any(), any(), any()))
                .thenReturn(Collections.singletonList(new Reservation()));

        assertThrows(ReservationConflictException.class, () -> {
            reservationService.createReservation(sampleRequest, "admin");
        });

        // Weryfikujemy, że metoda save NIGDY nie została wywołana ze względu na błąd
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void deleteReservation_ShouldThrowException_WhenUserIsNotTheOwner() {
        AppUser owner = new AppUser("Jan", "pass", "USER");
        Reservation existingReservation = new Reservation(Sector.SECTOR_A, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        existingReservation.setAppUser(owner);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(existingReservation));

        assertThrows(RuntimeException.class, () -> {
            reservationService.deleteReservation(1);
        });

        verify(reservationRepository, never()).deleteById(any());
    }
}