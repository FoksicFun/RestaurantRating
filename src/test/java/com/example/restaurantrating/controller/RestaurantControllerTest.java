package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRestaurant_shouldReturnCreated() throws Exception {
        // Arrange
        RestaurantRequest request = new RestaurantRequest(
                "La Trattoria", "Итальянская кухня",
                Restaurant.CuisineType.ITALIAN, 1500
        );
        RestaurantResponse response = new RestaurantResponse(
                1L, "La Trattoria", "Итальянская кухня",
                Restaurant.CuisineType.ITALIAN, 1500, BigDecimal.valueOf(4.5)
        );

        when(restaurantService.save(any(RestaurantRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("La Trattoria"))
                .andExpect(jsonPath("$.rating").value(4.5));
    }

    @Test
    void getAllRestaurants_shouldReturnList() throws Exception {
        // Arrange
        RestaurantResponse r1 = new RestaurantResponse(
                1L, "La Trattoria", "Описание",
                Restaurant.CuisineType.ITALIAN, 1500, BigDecimal.valueOf(4.5)
        );
        RestaurantResponse r2 = new RestaurantResponse(
                2L, "Sakura", "Описание",
                Restaurant.CuisineType.JAPANESE, 2000, BigDecimal.valueOf(4.8)
        );

        when(restaurantService.findAll()).thenReturn(List.of(r1, r2));

        // Act & Assert
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("La Trattoria"))
                .andExpect(jsonPath("$[0].rating").value(4.5));
    }

    @Test
    void deleteRestaurant_shouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());

        verify(restaurantService, times(1)).remove(1L);
    }
}