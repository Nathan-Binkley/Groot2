package com.restaurant.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private String reviewerName;
    private String content;
    private Integer rating;
    private LocalDateTime createdAt;
    private String reviewType;  // "RESTAURANT" or "MENU_ITEM"
    private Long menuItemId;  // Only used for menu item reviews
    private String menuItemName; // For displaying the menu item name
    private Long restaurantId; // Only used for restaurant reviews
    private String restaurantName; // For displaying the restaurant name
    private boolean approved;
    
    // Constructors
    public ReviewDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getReviewType() {
        return reviewType;
    }
    
    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }
    
    public Long getMenuItemId() {
        return menuItemId;
    }
    
    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }
    
    public String getMenuItemName() {
        return menuItemName;
    }
    
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    
    public Long getRestaurantId() {
        return restaurantId;
    }
    
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
    
    public String getRestaurantName() {
        return restaurantName;
    }
    
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
} 