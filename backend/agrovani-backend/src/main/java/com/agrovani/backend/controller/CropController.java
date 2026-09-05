package com.agrovani.backend.controller;

import com.agrovani.backend.entity.Crop;
import com.agrovani.backend.repository.CropRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropRepository cropRepository;

    public CropController(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getActiveCrops() {
        return ResponseEntity.ok(cropRepository.findByIsActiveTrue());
    }
}