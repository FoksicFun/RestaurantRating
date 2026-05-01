package com.example.restaurantrating.dto;

import com.example.restaurantrating.entity.Restaurant.CuisineType;

public record RestaurantRequest(
        String name,
        String description,
        CuisineType cuisineType,
        Integer averageCheckPerPerson
) {}