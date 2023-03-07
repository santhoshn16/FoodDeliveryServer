package com.example.springsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MENUS")
public class MenuItems {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer price;
    private String description;
    private String category;

    public MenuItems(String name, Integer price, String description, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}
