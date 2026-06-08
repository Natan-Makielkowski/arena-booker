package com.example.arena_booker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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


}
