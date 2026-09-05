package com.agrovani.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FarmRequest {

    @Size(max = 80, message = "Label must not exceed 80 characters")
    private String label;

    @DecimalMin(value = "0.01", message = "Area must be greater than zero")
    @DecimalMax(value = "999999.99", message = "Area is unrealistically large")
    private BigDecimal areaAcres;

    @Size(max = 100)
    private String village;

    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private BigDecimal longitude;

    @Size(max = 40)
    private String soilType;

    @Size(max = 40)
    private String irrigationType;
}