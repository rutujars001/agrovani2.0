package com.agrovani.backend.dto;

import com.agrovani.backend.entity.Farm;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FarmResponse {

    private final Long id;
    private final Long farmerId;
    private final String label;
    private final BigDecimal areaAcres;
    private final String village;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String soilType;
    private final String irrigationType;
    private final LocalDateTime createdAt;

    public FarmResponse(Farm farm) {
        this.id = farm.getId();
        this.farmerId = farm.getFarmer().getId();
        this.label = farm.getLabel();
        this.areaAcres = farm.getAreaAcres();
        this.village = farm.getVillage();
        this.latitude = farm.getLatitude();
        this.longitude = farm.getLongitude();
        this.soilType = farm.getSoilType();
        this.irrigationType = farm.getIrrigationType();
        this.createdAt = farm.getCreatedAt();
    }
}