package com.example.restaurantrating.dto;

import java.time.LocalDate;

public record VisitorResponse(
        Long id,
        String name,
        Integer age,
        String gender,
        LocalDate registrationDate
) {}