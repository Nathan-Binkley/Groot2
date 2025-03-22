package com.restaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.MenuItemDTO;
import com.restaurant.model.MenuItem;
import com.restaurant.repository.MenuItemRepository;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = convertToEntity(menuItemDTO);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return convertToDTO(savedMenuItem);
    }

    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<MenuItemDTO> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getAvailableMenuItems() {
        return menuItemRepository.findByAvailable(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        Optional<MenuItem> existingItem = menuItemRepository.findById(id);
        
        if (existingItem.isPresent()) {
            MenuItem menuItem = existingItem.get();
            
            // Update fields
            menuItem.setName(menuItemDTO.getName());
            menuItem.setDescription(menuItemDTO.getDescription());
            menuItem.setPrice(menuItemDTO.getPrice());
            menuItem.setCategory(menuItemDTO.getCategory());
            menuItem.setImageUrl(menuItemDTO.getImageUrl());
            menuItem.setAvailable(menuItemDTO.isAvailable());
            
            MenuItem updatedItem = menuItemRepository.save(menuItem);
            return convertToDTO(updatedItem);
        }
        
        return null;
    }

    public boolean deleteMenuItem(Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper methods to convert between Entity and DTO
    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setCategory(menuItem.getCategory());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setAvailable(menuItem.isAvailable());
        return dto;
    }

    private MenuItem convertToEntity(MenuItemDTO dto) {
        MenuItem menuItem = new MenuItem();
        // Don't set ID for new entities, or set it for updates
        if (dto.getId() != null) {
            menuItem.setId(dto.getId());
        }
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setAvailable(dto.isAvailable());
        return menuItem;
    }
} 