package com.example.springsecurity.service;

import com.example.springsecurity.dto.ItemtoRestaurantForm;
import com.example.springsecurity.models.MenuItems;

import java.util.List;

public interface MenuItemsService {

    List<MenuItems> getMenuItems(String name);

    MenuItems saveMenuItem(ItemtoRestaurantForm form);

}
