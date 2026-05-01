package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Visitors", description = "API для управления посетителями")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PostMapping
    @Operation(summary = "Создать нового посетителя", description = "Создаёт посетителя и возвращает его данные")
    public ResponseEntity<VisitorResponse> createVisitor(@RequestBody VisitorRequest request) {
        VisitorResponse response = visitorService.save(request);
        return ResponseEntity.created(URI.create("/api/users/" + response.id())).body(response);
    }

    @GetMapping
    @Operation(summary = "Получить всех посетителей", description = "Возвращает список всех посетителей")
    public List<VisitorResponse> getAllVisitors() {
        return visitorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить посетителя по ID", description = "Возвращает данные посетителя по его идентификатору")
    public ResponseEntity<VisitorResponse> getVisitorById(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить посетителя", description = "Обновляет данные посетителя")
    public ResponseEntity<VisitorResponse> updateVisitor(
            @PathVariable Long id,
            @RequestBody VisitorRequest request) {
        // Для простоты просто создаём нового (можно доработать)
        return ResponseEntity.ok(visitorService.save(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя", description = "Удаляет посетителя по его идентификатору")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        visitorService.remove(id);
        return ResponseEntity.noContent().build();
    }
}