package com.agrovani.backend.controller;

import com.agrovani.backend.dto.FarmerRegistrationRequest;
import com.agrovani.backend.dto.FarmerResponse;
import com.agrovani.backend.service.FarmerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping("/register")
    public ResponseEntity<FarmerResponse> register(
            @Valid @RequestBody FarmerRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(farmerService.register(request));
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<FarmerResponse> getByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(farmerService.getByPhoneNumber(phoneNumber));
    }

    @GetMapping
    public ResponseEntity<List<FarmerResponse>> getAll() {
        return ResponseEntity.ok(farmerService.getAll());
    }
}