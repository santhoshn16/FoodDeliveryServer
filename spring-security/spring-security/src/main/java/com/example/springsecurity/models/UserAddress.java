package com.example.springsecurity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "USERADDRESS")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Street;
    private String City;
    private Integer zip;

    public UserAddress(String street, String city, Integer zip){
        this.setStreet(street);
        this.setCity(city);
        this.setZip(zip);
    }

//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof UserAddress )) return false;
//        return id != null && id.equals(((UserAddress) o).getId());
//    }
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
