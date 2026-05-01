package com.example.restaurantrating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class RestaurantRatingApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RestaurantRatingApplication.class, args);

		// Блокируем главный поток
		new CountDownLatch(1).await();
	}
}