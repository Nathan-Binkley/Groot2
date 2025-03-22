package com.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.ReviewDTO;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Restaurant;
import com.restaurant.model.Review;
import com.restaurant.repository.MenuItemRepository;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, 
                         MenuItemRepository menuItemRepository,
                         RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        
        // Set creation time
        review.setCreatedAt(LocalDateTime.now());
        
        // For menu item reviews, validate and set the menu item
        if ("MENU_ITEM".equals(reviewDTO.getReviewType()) && reviewDTO.getMenuItemId() != null) {
            Optional<MenuItem> menuItem = menuItemRepository.findById(reviewDTO.getMenuItemId());
            if (menuItem.isPresent()) {
                review.setMenuItem(menuItem.get());
            } else {
                throw new IllegalArgumentException("Menu item not found with ID: " + reviewDTO.getMenuItemId());
            }
        }
        
        // For restaurant reviews, validate and set the restaurant
        if ("RESTAURANT".equals(reviewDTO.getReviewType()) && reviewDTO.getRestaurantId() != null) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId());
            if (restaurant.isPresent()) {
                review.setRestaurant(restaurant.get());
            } else {
                throw new IllegalArgumentException("Restaurant not found with ID: " + reviewDTO.getRestaurantId());
            }
        }
        
        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ReviewDTO> getApprovedReviews() {
        return reviewRepository.findByApproved(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getRestaurantReviews(boolean approvedOnly) {
        if (approvedOnly) {
            return reviewRepository.findByReviewTypeAndApproved("RESTAURANT", true).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return reviewRepository.findByReviewType("RESTAURANT").stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<ReviewDTO> getMenuItemReviews(Long menuItemId, boolean approvedOnly) {
        if (approvedOnly) {
            return reviewRepository.findByMenuItemIdAndApproved(menuItemId, true).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return reviewRepository.findByMenuItemId(menuItemId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            
            // Update fields that are allowed to be updated
            review.setContent(reviewDTO.getContent());
            review.setRating(reviewDTO.getRating());
            review.setApproved(reviewDTO.isApproved());
            
            // If menu item ID changed, update the menu item reference
            if ("MENU_ITEM".equals(reviewDTO.getReviewType()) && 
                    !review.getMenuItem().getId().equals(reviewDTO.getMenuItemId())) {
                Optional<MenuItem> menuItem = menuItemRepository.findById(reviewDTO.getMenuItemId());
                menuItem.ifPresent(review::setMenuItem);
            }
            
            Review updatedReview = reviewRepository.save(review);
            return convertToDTO(updatedReview);
        }
        
        return null;
    }
    
    public ReviewDTO approveReview(Long id) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setApproved(true);
            Review updatedReview = reviewRepository.save(review);
            return convertToDTO(updatedReview);
        }
        
        return null;
    }

    public boolean deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper methods to convert between Entity and DTO
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setReviewType(review.getReviewType());
        dto.setApproved(review.isApproved());
        
        // Handle menu item relationship for menu item reviews
        if (review.getMenuItem() != null) {
            dto.setMenuItemId(review.getMenuItem().getId());
            dto.setMenuItemName(review.getMenuItem().getName());
        }
        
        // Handle restaurant relationship for restaurant reviews
        if (review.getRestaurant() != null) {
            dto.setRestaurantId(review.getRestaurant().getId());
            dto.setRestaurantName(review.getRestaurant().getName());
        }
        
        return dto;
    }

    private Review convertToEntity(ReviewDTO dto) {
        Review review = new Review();
        
        // Don't set ID for new entities, or set it for updates
        if (dto.getId() != null) {
            review.setId(dto.getId());
        }
        
        review.setReviewerName(dto.getReviewerName());
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setReviewType(dto.getReviewType());
        review.setApproved(dto.isApproved());
        
        // For creation time, either use the DTO value or set current time
        if (dto.getCreatedAt() != null) {
            review.setCreatedAt(dto.getCreatedAt());
        } else {
            review.setCreatedAt(LocalDateTime.now());
        }
        
        // MenuItem relationship is handled in the service methods
        
        return review;
    }
} 