package com.example.springsecurity.service;

import com.example.springsecurity.dto.AddressToUserForm;
import com.example.springsecurity.models.Role;
import com.example.springsecurity.models.User;
import com.example.springsecurity.models.UserAddress;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<UserAddress> addAddressToUser(AddressToUserForm form);
    List<UserAddress> removeAddressOfUser(String name, Long id);
    User getUser(String username);
    User getUserByID(Long id);
    List<User> getUsers();

    User updateProfile(User user);

    String deleteUser(String name);
}
