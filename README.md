# Restaurant Rating System

Система оценки ресторанов на **Spring Boot** с автоматическим пересчётом рейтингов.

## 📋 Описание

Проект представляет собой систему для управления ресторанами, посетителями и их отзывами. 
При добавлении или удалении отзыва средний рейтинг ресторана пересчитывается автоматически.

## Функционал

- Управление посетителями (добавление, удаление, поиск)
- Управление ресторанами с различными типами кухни
- Система отзывов и оценок (1-5 звёзд)
- Автоматический пересчёт рейтинга** ресторана
- Анонимные отзывы (имя посетителя необязательно)
- H2 база данных для тестирования

## Технологии

- **Java 17+**
- **Spring Boot 3.2.x**
- **Spring Data JPA**
- **H2 Database** (встроенная БД)
- **Lombok** (для упрощения кода)
- **Maven** (сборка проекта)

## 📁 Структура проекта

restaurant-rating/
├── src/main/java/com/example/restaurantrating/
│ ├── entity/ # Сущности базы данных
│ │ ├── Visitor.java # Посетитель
│ │ ├── Restaurant.java # Ресторан
│ │ └── Review.java # Отзыв
│ ├── repository/ # Репозитории (Data Layer)
│ │ ├── VisitorRepository.java
│ │ ├── RestaurantRepository.java
│ │ └── ReviewRepository.java
│ ├── service/ # Сервисы (Business Logic)
│ │ ├── VisitorService.java
│ │ ├── RestaurantService.java
│ │ └── ReviewService.java
│ ├── Main.java # Тестирование (CommandLineRunner)
│ └── RestaurantRatingApplication.java
└── src/main/resources/
└── application.properties # Настройки приложения


## 🚀 Запуск проекта

### Требования
- JDK 17 или выше
- Maven 3.6+

### Установка и запуск

Скачать проект и запустить файл RestaurantRatingApplication
