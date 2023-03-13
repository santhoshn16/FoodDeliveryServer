package com.example.springsecurity.repository;

import com.example.springsecurity.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRespository extends JpaRepository<Restaurant, Long> {

    Restaurant findByUsername(String username);
}
