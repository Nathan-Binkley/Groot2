package com.restaurant.service;

import com.restaurant.dto.AddressDTO;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.model.Address;
import com.restaurant.model.Restaurant;
import com.restaurant.model.RestaurantCategory;
import com.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = convertToEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToDTO(savedRestaurant);
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RestaurantDTO> getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<RestaurantDTO> getRestaurantsByCategory(RestaurantCategory category) {
        return restaurantRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RestaurantDTO> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        
        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            
            // Update fields
            restaurant.setName(restaurantDTO.getName());
            restaurant.setDescription(restaurantDTO.getDescription());
            
            // Handle address conversion
            if (restaurantDTO.getAddress() != null) {
                Address address = restaurant.getAddress();
                if (address == null) {
                    address = new Address();
                }
                // Map AddressDTO to Address entity
                address.setStreet(restaurantDTO.getAddress().getStreet());
                address.setCity(restaurantDTO.getAddress().getCity());
                address.setState(restaurantDTO.getAddress().getState());
                address.setZipCode(restaurantDTO.getAddress().getZipCode());
                address.setCountry(restaurantDTO.getAddress().getCountry());
                restaurant.setAddress(address);
            }
            
            restaurant.setPhone(restaurantDTO.getPhone());
            restaurant.setEmail(restaurantDTO.getEmail());
            restaurant.setWebsite(restaurantDTO.getWebsite());
            restaurant.setOpeningTime(restaurantDTO.getOpeningTime());
            restaurant.setClosingTime(restaurantDTO.getClosingTime());
            restaurant.setCategory(restaurantDTO.getCategory());
            
            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
            return convertToDTO(updatedRestaurant);
        }
        
        return null;
    }

    public boolean deleteRestaurant(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper methods to convert between Entity and DTO
    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        
        // Handle address conversion
        if (restaurant.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(restaurant.getAddress().getId());
            addressDTO.setStreet(restaurant.getAddress().getStreet());
            addressDTO.setCity(restaurant.getAddress().getCity());
            addressDTO.setState(restaurant.getAddress().getState());
            addressDTO.setZipCode(restaurant.getAddress().getZipCode());
            addressDTO.setCountry(restaurant.getAddress().getCountry());
            dto.setAddress(addressDTO);
        }
        
        dto.setPhone(restaurant.getPhone());
        dto.setEmail(restaurant.getEmail());
        dto.setWebsite(restaurant.getWebsite());
        dto.setOpeningTime(restaurant.getOpeningTime());
        dto.setClosingTime(restaurant.getClosingTime());
        dto.setCategory(restaurant.getCategory());
        
        if (restaurant.getCategory() != null) {
            dto.setCategoryDisplayName(restaurant.getCategory().getDisplayName());
        }
        
        return dto;
    }

    private Restaurant convertToEntity(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        
        // Don't set ID for new entities, or set it for updates
        if (dto.getId() != null) {
            restaurant.setId(dto.getId());
        }
        
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        
        // Handle address conversion
        if (dto.getAddress() != null) {
            Address address = new Address();
            if (dto.getAddress().getId() != null) {
                address.setId(dto.getAddress().getId());
            }
            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());
            address.setZipCode(dto.getAddress().getZipCode());
            address.setCountry(dto.getAddress().getCountry());
            restaurant.setAddress(address);
        }
        
        restaurant.setPhone(dto.getPhone());
        restaurant.setEmail(dto.getEmail());
        restaurant.setWebsite(dto.getWebsite());
        restaurant.setOpeningTime(dto.getOpeningTime());
        restaurant.setClosingTime(dto.getClosingTime());
        restaurant.setCategory(dto.getCategory());
        
        return restaurant;
    }
} 