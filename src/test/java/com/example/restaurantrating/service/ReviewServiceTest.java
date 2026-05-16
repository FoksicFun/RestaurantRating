package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.ReviewRequest;
import com.example.restaurantrating.dto.ReviewResponse;
import com.example.restaurantrating.entity.Review;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.repository.ReviewRepository;
import com.example.restaurantrating.repository.RestaurantRepository;
import com.example.restaurantrating.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void save_shouldCreateReview() {

        Visitor visitor = new Visitor("Иван", 25, "M");
        visitor.setId(1L);

        Restaurant restaurant = new Restaurant("La Trattoria", "Описание",
                Restaurant.CuisineType.ITALIAN, 1500);
        restaurant.setId(2L);

        ReviewRequest request = new ReviewRequest(1L, 2L, 5, "Отлично!");
        Review review = new Review(visitor, restaurant, 5, "Отлично!");
        review.setId(10L);

        when(visitorRepository.findById(any(Long.class))).thenReturn(Optional.of(visitor));
        when(restaurantRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurant));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse response = reviewService.save(request);

        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals(5, response.rating());
        assertEquals("Отлично!", response.comment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void remove_shouldDeleteReview() {

        Visitor visitor = new Visitor("Иван", 25, "M");
        visitor.setId(1L);

        Restaurant restaurant = new Restaurant("La Trattoria", "Описание",
                Restaurant.CuisineType.ITALIAN, 1500);
        restaurant.setId(2L);

        Review review = new Review(visitor, restaurant, 5, "Отлично!");
        review.setId(10L);

        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(review));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        reviewService.remove(10L);

        verify(reviewRepository, times(1)).delete(review);
        verify(restaurantRepository, times(1)).save(restaurant);
    }
}