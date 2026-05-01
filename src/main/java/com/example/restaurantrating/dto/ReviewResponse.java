package com.example.restaurantrating.dto;

public record ReviewResponse(
        Long id,
        Long visitorId,
        String visitorName,
        Long restaurantId,
        String restaurantName,
        Integer rating,
        String comment
) {}