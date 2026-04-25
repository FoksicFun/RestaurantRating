package com.example.restaurantrating.repository;

import com.example.restaurantrating.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findById(long id);

    List<Restaurant> findByCuisineType(Restaurant.CuisineType cuisineType);
    List<Restaurant> findByNameContaining(String namePart);
}