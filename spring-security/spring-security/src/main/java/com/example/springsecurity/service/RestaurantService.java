package com.example.springsecurity.service;

import com.example.springsecurity.dto.AddressToRestaurantForm;
import com.example.springsecurity.models.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getRestaurants();

    Restaurant saveRestaurant(Restaurant restaurant);

    void addAddressToRestaurant(AddressToRestaurantForm form);

    Restaurant getRestaurant(Long name);


}
