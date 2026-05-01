package com.example.restaurantrating.dto;

import com.example.restaurantrating.entity.Restaurant.CuisineType;
import java.math.BigDecimal;

public record RestaurantResponse(
        Long id,
        String name,
        String description,
        CuisineType cuisineType,
        Integer averageCheckPerPerson,
        BigDecimal rating
) {}