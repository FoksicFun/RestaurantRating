package com.example.restaurantrating;

import com.example.restaurantrating.entity.Restaurant;
import com.example.restaurantrating.entity.Review;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.service.RestaurantService;
import com.example.restaurantrating.service.ReviewService;
import com.example.restaurantrating.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ДОБАВЛЕНИЕ ПОСЕТИТЕЛЕЙ");
        Visitor visitor1 = new Visitor("Иван Петров", 28, "M");
        Visitor visitor2 = new Visitor("Мария Сидорова", 32, "F");
        Visitor visitor3 = new Visitor(null, 25, "M"); // Анонимный посетитель

        visitorService.save(visitor1);
        visitorService.save(visitor2);
        visitorService.save(visitor3);

        System.out.println("Добавлено 3 посетителя");

        System.out.println("\nОБАВЛЕНИЕ РЕСТОРАНОВ");
        Restaurant restaurant1 = new Restaurant(
                "La Trattoria",
                "Аутентичная итальянская кухня",
                Restaurant.CuisineType.ITALIAN,
                1500
        );

        Restaurant restaurant2 = new Restaurant(
                "Sakura",
                "Японские суши и роллы",
                Restaurant.CuisineType.JAPANESE,
                2000
        );

        Restaurant restaurant3 = new Restaurant(
                "Burger House",
                "Лучшие бургеры в городе",
                Restaurant.CuisineType.AMERICAN,
                800
        );

        restaurantService.save(restaurant1);
        restaurantService.save(restaurant2);
        restaurantService.save(restaurant3);

        System.out.println("Добавлено 3 ресторана");

        System.out.println("\nДОБАВЛЕНИЕ ОТЗЫВОВ");
        // Отзыв 1
        Review review1 = new Review(visitor1, restaurant1, 5, "Отличная паста!");
        reviewService.save(review1);

        // Отзыв 2
        Review review2 = new Review(visitor2, restaurant1, 4, "Вкусно, но дороговато");
        reviewService.save(review2);

        // Отзыв 3
        Review review3 = new Review(visitor1, restaurant2, 5, "Лучшие суши!");
        reviewService.save(review3);

        // Отзыв 4
        Review review4 = new Review(visitor3, restaurant2, 3, "Средне");
        reviewService.save(review4);

        // Отзыв 5
        Review review5 = new Review(visitor2, restaurant3, 4, "Хорошие бургеры");
        reviewService.save(review5);

        System.out.println("Добавлено 5 отзывов");

        System.out.println("\nВСЕ РЕСТОРАНЫ С РЕЙТИНГАМИ");
        for (Restaurant r : restaurantService.findAll()) {
            System.out.printf("🍽 %s (%s) - Рейтинг: %.1f, Средний чек: %d руб.\n",
                    r.getName(),
                    r.getCuisineType(),
                    r.getRating(),
                    r.getAverageCheckPerPerson());
        }

        System.out.println("\nВСЕ ОТЗЫВЫ");
        for (Review r : reviewService.findAll()) {
            String visitorName = r.getVisitor().getName() != null ?
                    r.getVisitor().getName() : "Аноним";
            System.out.printf(" %s → %s: %d/5 (%s)\n",
                    visitorName,
                    r.getRestaurant().getName(),
                    r.getRating(),
                    r.getComment() != null ? r.getComment() : "без комментария");
        }

        System.out.println("\nУДАЛЕНИЕ ОТЗЫВА И ПЕРЕСЧЕТ РЕЙТИНГА");
        System.out.println("Рейтинги до удаления:");
        System.out.println("La Trattoria: " + restaurant1.getRating());

        reviewService.remove(review2); // Удаляем отзыв Марии о La Trattoria

        System.out.println("Рейтинги после удаления отзыва:");
        System.out.println("La Trattoria: " + restaurant1.getRating());
    }
}