package com.example.restaurantrating.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;              // кто оставил отзыв

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;        // о каком ресторане

    private Integer rating;               // оценка (1-5)

    private String comment;               // текст отзыва (может быть пустым)

    // Конструктор
    public Review(Visitor visitor, Restaurant restaurant, Integer rating, String comment) {
        this.visitor = visitor;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }
}