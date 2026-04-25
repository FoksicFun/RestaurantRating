package com.example.restaurantrating.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;                    // название ресторана

    private String description;             // описание (может быть пустым)

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;        // тип кухни

    private Integer averageCheckPerPerson;  // средний чек на человека

    private BigDecimal rating;              // средняя оценка (0.0 - 5.0)

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();  // все отзывы

    // Конструктор
    public Restaurant(String name, String description, CuisineType cuisineType, Integer averageCheckPerPerson) {
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.averageCheckPerPerson = averageCheckPerPerson;
        this.rating = BigDecimal.ZERO;
    }

    // Метод для пересчета средней оценки
    public void recalculateRating() {
        if (reviews.isEmpty()) {
            this.rating = BigDecimal.ZERO;
        } else {
            int totalRating = reviews.stream()
                    .mapToInt(Review::getRating)  // используем mapToInt вместо map
                    .sum();

            this.rating = BigDecimal.valueOf((double) totalRating / reviews.size())
                    .setScale(1, RoundingMode.HALF_UP);
        }
    }

    // Перечисление типов кухни
    public enum CuisineType {
        EUROPEAN,      // Европейская
        ITALIAN,       // Итальянская
        CHINESE,       // Китайская
        JAPANESE,      // Японская
        AMERICAN,      // Американская
        INDIAN,        // Индийская
        MEDITERRANEAN  // Средиземноморская
    }
}