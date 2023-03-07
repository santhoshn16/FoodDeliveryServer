package com.example.springsecurity.service;

import com.example.springsecurity.dto.ItemtoRestaurantForm;
import com.example.springsecurity.models.MenuItems;
import com.example.springsecurity.models.Restaurant;
import com.example.springsecurity.repository.MenuItemsRepository;
import com.example.springsecurity.repository.RestaurantRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuItemsServiceImpl implements MenuItemsService{
    @Autowired
    private final MenuItemsRepository menuItemsRepository;
    @Autowired
    private final RestaurantRespository restaurantRespository;

    @Override
    public List<MenuItems> getMenuItems(String name) {
        Restaurant restaurant = restaurantRespository.findByUsername(name);
        log.info("Retrieved restauant {} information for listing menu items ",restaurant.getUsername());
        List<MenuItems> items = menuItemsRepository.findAllByRestaurantId(restaurant.getId());
        log.info("Retrieved Menu items for restaurant {}",items);
        return items;
    }

    @Override
    public MenuItems saveMenuItem(ItemtoRestaurantForm form) {
        Restaurant restaurant = restaurantRespository.findByUsername(form.getRestaurantName());
        MenuItems menuItems = new MenuItems(form.getName(), form.getPrice(), form.getDescription(),
                form.getCategory());
        restaurant.getMenuItems().add(menuItems);
        log.info("Saved Item to restaurant {}", restaurant.getUsername());
        return menuItems;
    }
}
