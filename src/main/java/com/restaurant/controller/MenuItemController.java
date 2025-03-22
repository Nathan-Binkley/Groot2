package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.MenuItemDTO;
import com.restaurant.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO createdItem = menuItemService.createMenuItem(menuItemDTO);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean available) {
        
        List<MenuItemDTO> menuItems;
        
        if (category != null) {
            menuItems = menuItemService.getMenuItemsByCategory(category);
        } else if (available != null && available) {
            menuItems = menuItemService.getAvailableMenuItems();
        } else {
            menuItems = menuItemService.getAllMenuItems();
        }
        
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO updatedItem = menuItemService.updateMenuItem(id, menuItemDTO);
        
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean deleted = menuItemService.deleteMenuItem(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
} 