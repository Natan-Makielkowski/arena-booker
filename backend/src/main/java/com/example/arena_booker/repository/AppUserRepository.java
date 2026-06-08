package com.example.arena_booker.repository;

import com.example.arena_booker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
}
