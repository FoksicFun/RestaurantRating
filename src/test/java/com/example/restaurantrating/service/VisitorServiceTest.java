package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void save_shouldCreateVisitor() {

        VisitorRequest request = new VisitorRequest("Иван", 25, "M");

        Visitor savedVisitor = new Visitor("Иван", 25, "M");
        savedVisitor.setId(1L);

        when(visitorRepository.save(any(Visitor.class))).thenReturn(savedVisitor);

        VisitorResponse response = visitorService.save(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Иван", response.name());
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    @Test
    void findById_shouldReturnVisitor() {

        Visitor visitor = new Visitor("Мария", 30, "F");
        visitor.setId(2L);

        when(visitorRepository.findById(any(Long.class))).thenReturn(Optional.of(visitor));

        VisitorResponse response = visitorService.findById(2L);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("Мария", response.name());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {

        when(visitorRepository.findById(any(Long.class))).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            visitorService.findById(999L);
        });

        assertEquals("Visitor not found with id: 999", exception.getMessage());
    }

    @Test
    void remove_shouldDeleteVisitor() {

        visitorService.remove(1L);

        verify(visitorRepository, times(1)).deleteById(1L);
    }
}