package com.restaurant.service;

import com.restaurant.model.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    
    Restaurant saveRestaurant(Restaurant restaurant);
    
    Optional<Restaurant> getRestaurantById(Long id);
    
    List<Restaurant> getAllRestaurants();
    
    List<Restaurant> getRestaurantsByZipCode(String zipCode);
    
    void deleteRestaurant(Long id);
} 