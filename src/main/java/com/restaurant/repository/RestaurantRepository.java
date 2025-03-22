package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantCategory;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByAddressZipCode(String zipCode);
    List<Restaurant> findByCategory(RestaurantCategory category);
    List<Restaurant> findByNameContainingIgnoreCase(String name);
} 