package com.restaurant.mapper;

import com.restaurant.dto.AddressDTO;
import com.restaurant.dto.MenuItemDTO;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.dto.ReviewDTO;
import com.restaurant.model.Address;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Restaurant;
import com.restaurant.model.Review;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantMapper {

    public Restaurant toEntity(RestaurantDTO dto) {
        if (dto == null) {
            return null;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setDescription(dto.getDescription());
        restaurant.setPhone(dto.getPhone());
        restaurant.setWebsite(dto.getWebsite());
        restaurant.setAddress(toAddressEntity(dto.getAddress()));
        
        if (dto.getMenuItems() != null) {
            restaurant.setMenuItems(dto.getMenuItems().stream()
                .map(this::toMenuItemEntity)
                .collect(Collectors.toList()));
        }
        
        if (dto.getReviews() != null) {
            restaurant.setReviews(dto.getReviews().stream()
                .map(this::toReviewEntity)
                .collect(Collectors.toList()));
        }
        
        return restaurant;
    }
    
    public RestaurantDTO toDto(Restaurant entity) {
        if (entity == null) {
            return null;
        }
        
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setWebsite(entity.getWebsite());
        dto.setAddress(toAddressDto(entity.getAddress()));
        
        if (entity.getMenuItems() != null) {
            dto.setMenuItems(entity.getMenuItems().stream()
                .map(this::toMenuItemDto)
                .collect(Collectors.toList()));
        }
        
        if (entity.getReviews() != null) {
            dto.setReviews(entity.getReviews().stream()
                .map(this::toReviewDto)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    private Address toAddressEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        
        return address;
    }
    
    private AddressDTO toAddressDto(Address entity) {
        if (entity == null) {
            return null;
        }
        
        AddressDTO dto = new AddressDTO();
        dto.setId(entity.getId());
        dto.setStreet(entity.getStreet());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setZipCode(entity.getZipCode());
        
        return dto;
    }
    
    private MenuItem toMenuItemEntity(MenuItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        
        return menuItem;
    }
    
    private MenuItemDTO toMenuItemDto(MenuItem entity) {
        if (entity == null) {
            return null;
        }
        
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        
        return dto;
    }
    
    private Review toReviewEntity(ReviewDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Review review = new Review();
        review.setId(dto.getId());
        review.setReviewerName(dto.getReviewerName());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(dto.getCreatedAt());
        
        return review;
    }
    
    private ReviewDTO toReviewDto(Review entity) {
        if (entity == null) {
            return null;
        }
        
        ReviewDTO dto = new ReviewDTO();
        dto.setId(entity.getId());
        dto.setReviewerName(entity.getReviewerName());
        dto.setRating(entity.getRating());
        dto.setComment(entity.getComment());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }
} 