package com.example.arena_booker.controller;


import com.example.arena_booker.dto.ReservationResponseDto;
import com.example.arena_booker.model.Reservation;
import com.example.arena_booker.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

}
