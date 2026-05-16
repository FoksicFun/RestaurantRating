package com.example.restaurantrating.controller;

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.service.VisitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createVisitor_shouldReturnCreated() throws Exception {
        // Arrange
        VisitorRequest request = new VisitorRequest("Иван", 25, "M");
        VisitorResponse response = new VisitorResponse(1L, "Иван", 25, "M", LocalDate.now());

        when(visitorService.save(any(VisitorRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Иван"));

        verify(visitorService, times(1)).save(any(VisitorRequest.class));
    }

    @Test
    void getAllVisitors_shouldReturnList() throws Exception {
        // Arrange
        VisitorResponse v1 = new VisitorResponse(1L, "Иван", 25, "M", LocalDate.now());
        VisitorResponse v2 = new VisitorResponse(2L, "Мария", 30, "F", LocalDate.now());

        when(visitorService.findAll()).thenReturn(List.of(v1, v2));

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Иван"))
                .andExpect(jsonPath("$[1].name").value("Мария"));
    }

    @Test
    void getVisitorById_shouldReturnVisitor() throws Exception {
        // Arrange
        VisitorResponse response = new VisitorResponse(1L, "Иван", 25, "M", LocalDate.now());

        when(visitorService.findById(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Иван"));
    }

    @Test
    void deleteVisitor_shouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(visitorService, times(1)).remove(1L);
    }
}