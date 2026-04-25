package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Найти по ID
    Review findById(long id);

    // Найти все отзывы о конкретном ресторане
    List<Review> findByRestaurantId(Long restaurantId);

    // Найти все отзывы конкретного посетителя
    List<Review> findByVisitorId(Long visitorId);
}