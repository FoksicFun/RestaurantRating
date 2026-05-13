package com.example.restaurantrating.config;

import com.example.restaurantrating.dto.RestaurantRequest;
import com.example.restaurantrating.dto.ReviewRequest;
import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.service.RestaurantService;
import com.example.restaurantrating.service.ReviewService;
import com.example.restaurantrating.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public void run(String... args) {
        System.out.println("ДОБАВЛЕНИЕ ТЕСТОВЫХ ДАННЫХ");

        // Создаём посетителей
        VisitorRequest visitor1Req = new VisitorRequest("Иван Петров", 28, "M");
        VisitorRequest visitor2Req = new VisitorRequest("Мария Сидорова", 32, "F");
        VisitorRequest visitor3Req = new VisitorRequest(null, 25, "M"); // Аноним

        var visitor1 = visitorService.save(visitor1Req);
        var visitor2 = visitorService.save(visitor2Req);
        var visitor3 = visitorService.save(visitor3Req);

        System.out.println("✓ Добавлено 3 посетителя");

        // Создаём рестораны
        RestaurantRequest rest1Req = new RestaurantRequest(
                "La Trattoria",
                "Аутентичная итальянская кухня",
                Restaurant.CuisineType.ITALIAN,
                1500
        );

        RestaurantRequest rest2Req = new RestaurantRequest(
                "Sakura",
                "Японские суши и роллы",
                Restaurant.CuisineType.JAPANESE,
                2000
        );

        RestaurantRequest rest3Req = new RestaurantRequest(
                "Burger House",
                "Лучшие бургеры в городе",
                Restaurant.CuisineType.AMERICAN,
                800
        );

        var restaurant1 = restaurantService.save(rest1Req);
        var restaurant2 = restaurantService.save(rest2Req);
        var restaurant3 = restaurantService.save(rest3Req);

        System.out.println("✓ Добавлено 3 ресторана");

        // Создаём отзывы
        ReviewRequest review1Req = new ReviewRequest(visitor1.id(), restaurant1.id(), 5, "Отличная паста!");
        ReviewRequest review2Req = new ReviewRequest(visitor2.id(), restaurant1.id(), 4, "Вкусно, но дороговато");
        ReviewRequest review3Req = new ReviewRequest(visitor1.id(), restaurant2.id(), 5, "Лучшие суши!");
        ReviewRequest review4Req = new ReviewRequest(visitor3.id(), restaurant2.id(), 3, "Средне");
        ReviewRequest review5Req = new ReviewRequest(visitor2.id(), restaurant3.id(), 4, "Хорошие бургеры");

        reviewService.save(review1Req);
        reviewService.save(review2Req);
        reviewService.save(review3Req);
        reviewService.save(review4Req);
        reviewService.save(review5Req);

        System.out.println("✓ Добавлено 5 отзывов");
        System.out.println("ГОТОВО! API доступно");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("API Docs: http://localhost:8080/v3/api-docs");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
    }
}