package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.RestaurantResponse;
import com.example.restaurantrating.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurants", description = "API для управления ресторанами")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    @Operation(summary = "Создать новый ресторан", description = "Создаёт ресторан и возвращает его данные")
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantRequest request) {
        RestaurantResponse response = restaurantService.save(request);
        return ResponseEntity.created(URI.create("/api/restaurants/" + response.id())).body(response);
    }

    @GetMapping
    @Operation(summary = "Получить все рестораны", description = "Возвращает список всех ресторанов с рейтингами")
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ресторан по ID", description = "Возвращает данные ресторана по его идентификатору")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан", description = "Обновляет данные ресторана")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.save(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан", description = "Удаляет ресторан по его идентификатору")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.remove(id);
        return ResponseEntity.noContent().build();
    }
}