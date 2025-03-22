package com.restaurant.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.model.RestaurantCategory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class RestaurantDTO {
    
    private Long id;
    
    @NotBlank(message = "Restaurant name is required")
    private String name;
    
    private String description;
    
    private String phone;
    
    private String email;
    
    private String website;
    
    @Valid
    private AddressDTO address;
    
    private List<MenuItemDTO> menuItems = new ArrayList<>();
    
    private List<ReviewDTO> reviews = new ArrayList<>();
    
    private LocalTime openingTime;
    
    private LocalTime closingTime;
    
    private RestaurantCategory category;
    
    private String categoryDisplayName;
    
    // Constructors
    public RestaurantDTO() {
    }
    
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    
    public LocalTime getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }
    
    public LocalTime getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
    
    public RestaurantCategory getCategory() {
        return category;
    }
    
    public void setCategory(RestaurantCategory category) {
        this.category = category;
        if (category != null) {
            this.categoryDisplayName = category.getDisplayName();
        }
    }
    
    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }
    
    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }
} 