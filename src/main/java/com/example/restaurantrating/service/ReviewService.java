package com.example.restaurantrating.service;

import com.example.restaurantrating.entity.Review;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantService restaurantService;

    // Сохранить отзыв (с пересчетом рейтинга ресторана)
    public Review save(Review review) {
        // Сохраняем отзыв
        Review savedReview = reviewRepository.save(review);

        // Получаем ресторан и пересчитываем его рейтинг
        Restaurant restaurant = review.getRestaurant();
        restaurant.getReviews().add(savedReview);
        restaurant.recalculateRating();
        restaurantService.save(restaurant);

        return savedReview;
    }

    // Удалить отзыв (с пересчетом рейтинга)
    public void remove(Review review) {
        Restaurant restaurant = review.getRestaurant();

        // Удаляем отзыв
        reviewRepository.delete(review);

        // Пересчитываем рейтинг ресторана
        restaurant.getReviews().remove(review);
        restaurant.recalculateRating();
        restaurantService.save(restaurant);
    }

    // Найти все отзывы
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    // Найти отзыв по ID
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // Найти все отзывы о ресторане
    public List<Review> findByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    // Найти все отзывы посетителя
    public List<Review> findByVisitor(Long visitorId) {
        return reviewRepository.findByVisitorId(visitorId);
    }
}