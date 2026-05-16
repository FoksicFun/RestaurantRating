package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.ReviewRequest;
import com.example.restaurantrating.dto.ReviewResponse;
import com.example.restaurantrating.entity.Review;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.repository.ReviewRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Transactional
    public ReviewResponse save(ReviewRequest request) {
        // Проверяем существование посетителя и ресторана
        Visitor visitor = visitorRepository.findById(request.visitorId())
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + request.visitorId()));

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + request.restaurantId()));

        // Создаём и сохраняем отзыв
        Review review = new Review(visitor, restaurant, request.rating(), request.comment());
        Review savedReview = reviewRepository.save(review);

        // Пересчитываем рейтинг ресторана
        restaurant.getReviews().add(savedReview);
        restaurant.recalculateRating();
        restaurantRepository.save(restaurant);

        return mapToResponse(savedReview);
    }

    @Transactional
    public void remove(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        Restaurant restaurant = review.getRestaurant();
        reviewRepository.delete(review);

        // Пересчитываем рейтинг
        restaurant.getReviews().remove(review);
        restaurant.recalculateRating();
        restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponse findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        return mapToResponse(review);
    }

    // Маппинг
    private ReviewResponse mapToResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getVisitor().getId(),
                review.getVisitor().getName(),
                review.getRestaurant().getId(),
                review.getRestaurant().getName(),
                review.getRating(),
                review.getComment()
        );
    }
}