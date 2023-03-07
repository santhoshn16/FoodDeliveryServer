package com.example.springsecurity.service;

import com.example.springsecurity.dto.AddressToUserForm;
import com.example.springsecurity.models.Role;
import com.example.springsecurity.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    void addAddressToUser(AddressToUserForm form);
    User getUser(String username);
    List<User> getUsers();
}
