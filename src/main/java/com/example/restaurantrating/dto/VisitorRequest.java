package com.example.restaurantrating.dto;

public record VisitorRequest(
        String name,
        Integer age,
        String gender
) {}