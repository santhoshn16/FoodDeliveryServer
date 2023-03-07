package com.example.springsecurity.service;

import com.example.springsecurity.dto.AddressToRestaurantForm;
import com.example.springsecurity.models.Restaurant;
import com.example.springsecurity.models.RestaurantAddress;
import com.example.springsecurity.repository.RestaurantRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private final RestaurantRespository restaurantRespository;
    @Override
    public List<Restaurant> getRestaurants() {
        log.info("fetching all restaurants");
        return restaurantRespository.findAll();
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        log.info("Restaurant {} saved to DB", restaurant.getUsername());
        return restaurantRespository.save(restaurant);
    }

    @Override
    public void addAddressToRestaurant(AddressToRestaurantForm form) {
        Restaurant restaurant = restaurantRespository.findByUsername(form.getRestaurantName());
        //log.info("{} found in DB", restaurant.getUsername());
        RestaurantAddress restaurantAddress =
                new RestaurantAddress(form.getStreet(), form.getCity(), form.getZip());
        restaurant.setAddress(restaurantAddress);
        log.info("Address updated to user {}", form.getRestaurantName());

    }

    public Restaurant getRestaurant(Long id) {
        Optional<Restaurant> restaurant = restaurantRespository.findById(id);
        log.info("Restaurant {} found in DB",restaurant.get().getUsername());
        return restaurant.get();
    }
}
