package com.example.arena_booker.controller;


import com.example.arena_booker.dto.ReservationRequestDto;
import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.Sector;
import com.example.arena_booker.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //@GetMapping
    //public List<ReservationResponseDto> getAllReservations(){
    //    return reservationService.getUsersReservations();
    //}

    @GetMapping
    public List<ReservationResponseDto> getMyReservations(Principal principal){
        return reservationService.getUsersReservations(principal.getName());
    }



    @PostMapping
    public ReservationResponseDto createReservation(@RequestBody @Valid ReservationRequestDto reservationRequestDto, Principal principal) throws Exception {
        return reservationService.createReservation(reservationRequestDto, principal.getName());
    }

    @PutMapping("/{id}")
    public void updateReservation(@PathVariable Integer id, @RequestBody @Valid ReservationRequestDto reservationRequestDto) throws Exception {
        reservationService.updateReservation(id, reservationRequestDto);
    }

    @GetMapping("/check")
    public boolean isTaken(@RequestParam Sector sector, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return reservationService.isTaken(sector, startTime, endTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id){
        reservationService.deleteReservation(id);

    }



}
