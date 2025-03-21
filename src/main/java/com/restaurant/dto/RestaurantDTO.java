package com.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDTO {
    
    private Long id;
    
    @NotBlank(message = "Restaurant name is required")
    private String name;
    
    private String description;
    
    private String phone;
    
    private String website;
    
    @Valid
    private AddressDTO address;
    
    private List<MenuItemDTO> menuItems = new ArrayList<>();
    
    private List<ReviewDTO> reviews = new ArrayList<>();
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public AddressDTO getAddress() {
        return address;
    }
    
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    
    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
    
    public List<ReviewDTO> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
} 