package com.example.springsecurity.repository;

import com.example.springsecurity.models.User;
import com.example.springsecurity.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllById(Long id);

    @Query(value = "SELECT * from USERADDRESS u where u.fuid = ?1",nativeQuery = true)
    List<UserAddress> findByUserId(Long id);
}
