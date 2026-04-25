package com.example.restaurantrating.service;

import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Сохранить ресторан
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // Удалить ресторан
    public void remove(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    // Найти все рестораны
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    // Найти ресторан по ID
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }
}