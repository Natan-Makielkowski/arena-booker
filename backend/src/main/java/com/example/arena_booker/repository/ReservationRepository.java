package com.example.arena_booker.repository;

import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findAllByAppUserUsername(String username);

    @Query("SELECT r FROM Reservation r WHERE r.sector = :sector AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> overlappingReservations(@Param("sector") Sector sector, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT r FROM Reservation r WHERE r.sector = :sector AND r.id != :reservationId AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> overlappingReservationsExcludingCurrent(
            @Param("reservationId") Integer id,
            @Param("sector") Sector sector,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime

    );
}
