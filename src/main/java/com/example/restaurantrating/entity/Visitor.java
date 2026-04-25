package com.example.restaurantrating.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "visitors")
@Data
@NoArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;           // имя (может быть null для анонимов)

    private Integer age;           // возраст

    private String gender;         // пол: "M" или "F"

    private LocalDate registrationDate; // дата регистрации

    // Конструктор с полями (без id)
    public Visitor(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.registrationDate = LocalDate.now();
    }
}