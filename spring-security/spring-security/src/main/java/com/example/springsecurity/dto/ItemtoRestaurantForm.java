package com.example.springsecurity.dto;

import lombok.Data;

@Data
public class ItemtoRestaurantForm {
    private String name;
    private Integer price;
    private String description;
    private String category;
    private String restaurantName;

}
