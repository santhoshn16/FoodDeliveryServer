package com.example.springsecurity.dto;

import lombok.Data;

@Data
public class AddressToUserForm {
    private String street;
    private String city;
    private Integer zip;
    private String username;
}
