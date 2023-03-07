package com.example.springsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTAURANTS")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private Long phone_number;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FRID", referencedColumnName = "id")
    private RestaurantAddress address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RID", referencedColumnName = "id")
    private Collection<MenuItems> menuItems;
}
