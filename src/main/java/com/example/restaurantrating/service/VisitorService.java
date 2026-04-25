package com.example.restaurantrating.service;

import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    // Сохранить посетителя
    public Visitor save(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    // Удалить посетителя
    public void remove(Visitor visitor) {
        visitorRepository.delete(visitor);
    }

    // Найти всех посетителей
    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    // Найти посетителя по ID
    public Visitor findById(Long id) {
        return visitorRepository.findById(id).orElse(null);
    }
}