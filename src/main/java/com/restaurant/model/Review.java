package com.restaurant.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String reviewerName;
    
    @Column(nullable = false, length = 1000)
    private String content;
    
    @Column(nullable = false)
    private Integer rating;  // Rating from 1-5
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private String reviewType;  // "RESTAURANT" or "MENU_ITEM"
    
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;  // Will be null for restaurant reviews
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant; // Optional, can be used for restaurant reviews
    
    // For filtering/moderation purposes
    @Column
    private boolean approved = false;
    
    // Constructors
    public Review() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Review(String reviewerName, String content, Integer rating, 
                 String reviewType, MenuItem menuItem, Restaurant restaurant) {
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
        this.reviewType = reviewType;
        this.menuItem = menuItem;
        this.restaurant = restaurant;
        this.createdAt = LocalDateTime.now();
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
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
} 