package com.restaurant.health;

import com.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RestaurantServiceHealthIndicator implements HealthIndicator {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantServiceHealthIndicator(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public Health health() {
        try {
            // Attempt to get all restaurants as a simple health check
            restaurantService.getAllRestaurants();
            return Health.up()
                    .withDetail("service", "Restaurant Service")
                    .withDetail("status", "Available")
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("service", "Restaurant Service")
                    .withDetail("status", "Unavailable")
                    .withDetail("error", ex.getMessage())
                    .build();
        }
    }
} 