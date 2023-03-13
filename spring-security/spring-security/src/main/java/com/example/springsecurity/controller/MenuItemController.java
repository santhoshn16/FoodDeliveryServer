package com.example.springsecurity.controller;

import com.example.springsecurity.dto.ItemtoRestaurantForm;
import com.example.springsecurity.models.MenuItems;
import com.example.springsecurity.models.Restaurant;
import com.example.springsecurity.service.MenuItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class MenuItemController {
    private final MenuItemsService menuItemsService;

    @GetMapping("{name}/menuitems")
    public ResponseEntity<List<MenuItems>> getMenuItems(@PathVariable String name){
        return ResponseEntity.ok().body(menuItemsService.getMenuItems(name));
    }

    @PostMapping("/saveItemToRestaurant")
    public ResponseEntity<MenuItems> saveItemToRestaurant(@RequestBody ItemtoRestaurantForm form){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
                path("/api/saveItemToRestaurant").toUriString());
        return ResponseEntity.created(uri).body(menuItemsService.saveMenuItem(form));
    }
}
