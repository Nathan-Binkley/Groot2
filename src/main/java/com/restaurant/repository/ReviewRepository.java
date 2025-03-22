package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewType(String reviewType);
    List<Review> findByMenuItemId(Long menuItemId);
    List<Review> findByApproved(boolean approved);
    List<Review> findByReviewTypeAndApproved(String reviewType, boolean approved);
    List<Review> findByMenuItemIdAndApproved(Long menuItemId, boolean approved);
} 