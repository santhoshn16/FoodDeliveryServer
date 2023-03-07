package com.example.springsecurity.repository;

import com.example.springsecurity.models.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemsRepository extends JpaRepository<MenuItems, Long> {

    List<MenuItems> findAllById(Long id);
    @Query(value = "SELECT * FROM MENUS m WHERE m.rid=?1",nativeQuery = true)
    List<MenuItems> findAllByRestaurantId(Long id);
}
