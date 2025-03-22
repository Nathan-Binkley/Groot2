package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ReviewDTO;
import com.restaurant.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO createdReview = reviewService.createReview(reviewDTO);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long menuItemId,
            @RequestParam(required = false, defaultValue = "true") boolean approvedOnly) {
        
        List<ReviewDTO> reviews;
        
        // Get reviews based on the query parameters
        if ("restaurant".equalsIgnoreCase(type)) {
            reviews = reviewService.getRestaurantReviews(approvedOnly);
        } else if ("menuItem".equalsIgnoreCase(type) && menuItemId != null) {
            reviews = reviewService.getMenuItemReviews(menuItemId, approvedOnly);
        } else if (approvedOnly) {
            reviews = reviewService.getApprovedReviews();
        } else {
            reviews = reviewService.getAllReviews();
        }
        
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ReviewDTO> approveReview(@PathVariable Long id) {
        ReviewDTO approvedReview = reviewService.approveReview(id);
        
        if (approvedReview != null) {
            return ResponseEntity.ok(approvedReview);
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
} 