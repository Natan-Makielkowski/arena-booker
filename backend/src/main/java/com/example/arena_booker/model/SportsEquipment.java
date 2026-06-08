package com.example.arena_booker.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SportsEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String equipmentName;
    private String totalQuantity;

    SportsEquipment() {}
    public SportsEquipment(String equipmentName, String totalQuantity) {
        this.equipmentName = equipmentName;
        this.totalQuantity = totalQuantity;
    }

    @ManyToMany
    private List<Reservation> reservations =  new ArrayList<>();


}
