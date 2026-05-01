package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.ReviewRequest;
import com.example.restaurantrating.dto.ReviewResponse;
import com.example.restaurantrating.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "API для управления отзывами")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Создать новый отзыв", description = "Создаёт отзыв о ресторане и пересчитывает рейтинг")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.save(request);
        return ResponseEntity.created(URI.create("/api/reviews/" + response.id())).body(response);
    }

    @GetMapping
    @Operation(summary = "Получить все отзывы", description = "Возвращает список всех отзывов")
    public List<ReviewResponse> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отзыв по ID", description = "Возвращает данные отзыва по его идентификатору")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв", description = "Удаляет отзыв и пересчитывает рейтинг ресторана")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.remove(id);
        return ResponseEntity.noContent().build();
    }
}