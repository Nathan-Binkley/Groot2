package com.restaurant.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.model.RestaurantCategory;
import com.restaurant.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant API", description = "API for managing restaurant information")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @Operation(
        summary = "Create a new restaurant",
        description = "Creates a new restaurant with the provided details",
        responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
        }
    )
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO createdRestaurant = restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
        summary = "Get all restaurants",
        description = "Returns a list of all restaurants"
    )
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            @RequestParam(required = false) String name) {
        
        List<RestaurantDTO> restaurants;
        
        if (category != null) {
            restaurants = restaurantService.getRestaurantsByCategory(category);
        } else if (name != null && !name.isEmpty()) {
            restaurants = restaurantService.searchRestaurantsByName(name);
        } else {
            restaurants = restaurantService.getAllRestaurants();
        }
        
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get restaurant by ID",
        description = "Returns a restaurant based on the provided ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant found"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
        }
    )
    public ResponseEntity<RestaurantDTO> getRestaurantById(
            @Parameter(description = "Restaurant ID") @PathVariable Long id) {
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update restaurant",
        description = "Updates an existing restaurant with the provided details",
        responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
        }
    )
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @Parameter(description = "Restaurant ID") @PathVariable Long id, 
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO);
        
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete restaurant",
        description = "Deletes a restaurant based on the provided ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
        }
    )
    public ResponseEntity<Void> deleteRestaurant(
            @Parameter(description = "Restaurant ID") @PathVariable Long id) {
        boolean deleted = restaurantService.deleteRestaurant(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryInfo>> getAllCategories() {
        List<CategoryInfo> categories = Arrays.stream(RestaurantCategory.values())
                .map(category -> new CategoryInfo(category.name(), category.getDisplayName()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(categories);
    }
    
    // Helper class for category info response
    private static class CategoryInfo {
        private String code;
        private String displayName;
        
        public CategoryInfo(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
} 