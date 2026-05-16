package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.ReviewRequest;
import com.example.restaurantrating.dto.ReviewResponse;
import com.example.restaurantrating.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createReview_shouldReturnCreated() throws Exception {
        // Arrange
        ReviewRequest request = new ReviewRequest(1L, 2L, 5, "Отлично!");
        ReviewResponse response = new ReviewResponse(
                10L, 1L, "Иван", 2L, "La Trattoria", 5, "Отлично!"
        );

        when(reviewService.save(any(ReviewRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Отлично!"));
    }

    @Test
    void getAllReviews_shouldReturnList() throws Exception {
        // Arrange
        ReviewResponse r1 = new ReviewResponse(
                10L, 1L, "Иван", 2L, "La Trattoria", 5, "Отлично!"
        );
        ReviewResponse r2 = new ReviewResponse(
                11L, 2L, "Мария", 2L, "La Trattoria", 4, "Вкусно"
        );

        when(reviewService.findAll()).thenReturn(List.of(r1, r2));

        // Act & Assert
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[1].rating").value(4));
    }

    @Test
    void getReviewById_shouldReturnReview() throws Exception {
        // Arrange
        ReviewResponse response = new ReviewResponse(
                10L, 1L, "Иван", 2L, "La Trattoria", 5, "Отлично!"
        );

        when(reviewService.findById(10L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/reviews/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void deleteReview_shouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/reviews/10"))
                .andExpect(status().isNoContent());

        verify(reviewService, times(1)).remove(10L);
    }
}