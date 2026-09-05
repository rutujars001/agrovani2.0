package com.agrovani.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FarmCropRequest {

    @NotBlank(message = "Crop code is required")
    private String cropCode;

    private LocalDate sowingDate;

    private LocalDate expectedHarvestDate;
}