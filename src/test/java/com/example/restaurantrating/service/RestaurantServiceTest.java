package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void save_shouldCreateRestaurant() {
        // Arrange
        RestaurantRequest request = new RestaurantRequest(
                "La Trattoria", "Итальянская кухня",
                Restaurant.CuisineType.ITALIAN, 1500
        );

        Restaurant savedRestaurant = new Restaurant(
                "La Trattoria", "Итальянская кухня",
                Restaurant.CuisineType.ITALIAN, 1500
        );
        savedRestaurant.setId(1L);
        savedRestaurant.setRating(BigDecimal.valueOf(4.5));

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(savedRestaurant);

        // Act
        RestaurantResponse response = restaurantService.save(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("La Trattoria", response.name());
        assertEquals(BigDecimal.valueOf(4.5), response.rating());
    }

    @Test
    void findById_shouldReturnRestaurant() {
        // Arrange
        Restaurant restaurant = new Restaurant("Sakura", "Японская кухня",
                Restaurant.CuisineType.JAPANESE, 2000);
        restaurant.setId(2L);
        restaurant.setRating(BigDecimal.valueOf(4.8));

        // ← ВАЖНО: any(Long.class) вместо anyLong()
        when(restaurantRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurant));

        // Act
        RestaurantResponse response = restaurantService.findById(2L);

        // Assert
        assertNotNull(response);
        assertEquals("Sakura", response.name());
        assertEquals(BigDecimal.valueOf(4.8), response.rating());
    }

    @Test
    void remove_shouldDeleteRestaurant() {
        // Act
        restaurantService.remove(1L);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(1L);
    }
}