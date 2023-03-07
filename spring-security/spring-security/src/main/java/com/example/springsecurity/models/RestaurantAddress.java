package com.example.springsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTADDRESS")
public class RestaurantAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Street;
    private String City;
    private Integer zip;

    public RestaurantAddress(String street, String city, Integer zip){
        this.setStreet(street);
        this.setCity(city);
        this.setZip(zip);
    }

}
