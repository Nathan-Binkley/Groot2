package com.restaurant.controller;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.mapper.RestaurantMapper;
import com.restaurant.model.Restaurant;
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
    private final RestaurantMapper restaurantMapper;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
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
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        return new ResponseEntity<>(restaurantMapper.toDto(savedRestaurant), HttpStatus.CREATED);
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
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(r -> new ResponseEntity<>(restaurantMapper.toDto(r), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @Operation(
        summary = "Get all restaurants",
        description = "Returns a list of all restaurants"
    )
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantDTO> restaurantDTOs = restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(restaurantDTOs, HttpStatus.OK);
    }

    @GetMapping("/zipcode/{zipCode}")
    @Operation(
        summary = "Get restaurants by zipcode",
        description = "Returns a list of restaurants in the specified zip code",
        responses = {
            @ApiResponse(responseCode = "200", description = "Restaurants found")
        }
    )
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByZipCode(
            @Parameter(description = "Zip code") @PathVariable String zipCode) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByZipCode(zipCode);
        List<RestaurantDTO> restaurantDTOs = restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(restaurantDTOs, HttpStatus.OK);
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
        Optional<Restaurant> existingRestaurant = restaurantService.getRestaurantById(id);
        if (existingRestaurant.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        restaurantDTO.setId(id);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(restaurant);
        return new ResponseEntity<>(restaurantMapper.toDto(updatedRestaurant), HttpStatus.OK);
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
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 