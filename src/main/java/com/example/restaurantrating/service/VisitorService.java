package com.example.restaurantrating.service;

import com.example.restaurantrating.dto.VisitorRequest;
import com.example.restaurantrating.dto.VisitorResponse;
import com.example.restaurantrating.entity.Visitor;
import com.example.restaurantrating.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Transactional
    public VisitorResponse save(VisitorRequest request) {
        Visitor visitor = new Visitor(
                request.name(),
                request.age(),
                request.gender()
        );

        Visitor savedVisitor = visitorRepository.save(visitor);
        return mapToResponse(savedVisitor);
    }

    @Transactional
    public void remove(Long id) {
        visitorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VisitorResponse findById(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with id: " + id));
        return mapToResponse(visitor);
    }

    private VisitorResponse mapToResponse(Visitor visitor) {
        return new VisitorResponse(
                visitor.getId(),
                visitor.getName(),
                visitor.getAge(),
                visitor.getGender(),
                visitor.getRegistrationDate()
        );
    }
}