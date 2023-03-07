package com.example.springsecurity.service;

import com.example.springsecurity.dto.AddressToUserForm;
import com.example.springsecurity.models.Role;
import com.example.springsecurity.models.User;
import com.example.springsecurity.models.UserAddress;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            log.error("user not found in database ");
            throw new UsernameNotFoundException("user not found in DB");
        } else {
            log.info("user found in database {}",user.getUsername());
        }
        Collection< SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                authorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("User {} saved to DB", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Role save to DB {}",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        log.info("Role {} added to User {}",roleName, username);
    }

    public void addAddressToUser(AddressToUserForm form){
        User user = userRepository.findByUsername(form.getUsername());
        UserAddress userAddress = new UserAddress(form.getStreet(), form.getCity(), form.getZip());
        //userAddress.setUser(user);
        user.getAddress().add(userAddress);
        log.info("Address updated to user {}", form.getUsername());
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }


}
