package com.example.restaurantrating.dto;

public record ReviewRequest(
        Long visitorId,
        Long restaurantId,
        Integer rating,
        String comment
) {}