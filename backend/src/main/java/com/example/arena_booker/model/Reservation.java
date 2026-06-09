package com.example.arena_booker.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Sector sector;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Reservation() {}
    public Reservation(Sector sector, LocalDateTime startTime, LocalDateTime endTime) {
        this.sector = sector;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    @ManyToOne
    @JoinColumn(name="app_user_id")
    private AppUser appUser;


    @ManyToMany
    @JoinTable(name = "reservation_sportsEquipment",
                joinColumns = @JoinColumn(name = "reservation_id"),
                inverseJoinColumns = @JoinColumn(name = "sports_equipment_id")

    )
    private List<SportsEquipment> sportsEquipmentList =  new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public Sector getSector() {
        return sector;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getDuration() {
        return Duration.between(startTime, endTime).toMinutes();
    }





}
