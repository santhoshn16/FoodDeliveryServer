package com.example.springsecurity.controller;

import com.example.springsecurity.dto.AddressToRestaurantForm;
import com.example.springsecurity.models.Restaurant;
import com.example.springsecurity.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public ResponseEntity</*Map<Long,String>*/List<Restaurant>> getRestaurants(){
//        List<Restaurant> restaurants = restaurantService.getRestaurants();
//        Map<Long, String> info = new HashMap<>();
//        for(Restaurant i: restaurants){
//            info.put(i.getId(), i.getUsername());
//        }
//        return ResponseEntity.ok().body(info);
        return ResponseEntity.ok().body(restaurantService.getRestaurants());
    }

    @PostMapping("/restaurant/save")
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/restaurant/save").toUriString());
        return ResponseEntity.created(uri).body(restaurantService.saveRestaurant(restaurant));
    }

    @PostMapping("/address/addtorestaurant")
    public ResponseEntity<?> saveAddressToUser(@RequestBody AddressToRestaurantForm form){
        restaurantService.addAddressToRestaurant(form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id){
        Optional<Restaurant> restaurant = Optional.ofNullable(restaurantService.getRestaurant(id));
        return ResponseEntity.ok().body(restaurant.get());
    }
}
