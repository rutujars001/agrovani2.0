package com.agrovani.backend.controller;

import com.agrovani.backend.dto.FarmCropRequest;
import com.agrovani.backend.dto.FarmCropResponse;
import com.agrovani.backend.dto.FarmRequest;
import com.agrovani.backend.dto.FarmResponse;
import com.agrovani.backend.service.FarmService;
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
@RequestMapping("/api")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping("/farmers/{farmerId}/farms")
    public ResponseEntity<FarmResponse> addFarm(@PathVariable Long farmerId,
                                                @Valid @RequestBody FarmRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(farmService.addFarm(farmerId, request));
    }

    @GetMapping("/farmers/{farmerId}/farms")
    public ResponseEntity<List<FarmResponse>> getFarms(@PathVariable Long farmerId) {
        return ResponseEntity.ok(farmService.getFarmsForFarmer(farmerId));
    }

    @PostMapping("/farms/{farmId}/crops")
    public ResponseEntity<FarmCropResponse> addCrop(@PathVariable Long farmId,
                                                    @Valid @RequestBody FarmCropRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(farmService.addCropToFarm(farmId, request));
    }

    @GetMapping("/farms/{farmId}/crops")
    public ResponseEntity<List<FarmCropResponse>> getCropsForFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(farmService.getCropsForFarm(farmId));
    }

    @GetMapping("/farmers/{farmerId}/crops")
    public ResponseEntity<List<FarmCropResponse>> getActiveCrops(@PathVariable Long farmerId) {
        return ResponseEntity.ok(farmService.getActiveCropsForFarmer(farmerId));
    }
}