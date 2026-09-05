package com.agrovani.backend.dto;

import com.agrovani.backend.entity.FarmCrop;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FarmCropResponse {

    private final Long id;
    private final Long farmId;
    private final String cropCode;
    private final String cropNameEn;
    private final String cropNameMr;
    private final LocalDate sowingDate;
    private final LocalDate expectedHarvestDate;
    private final String status;

    public FarmCropResponse(FarmCrop farmCrop) {
        this.id = farmCrop.getId();
        this.farmId = farmCrop.getFarm().getId();
        this.cropCode = farmCrop.getCrop().getCode();
        this.cropNameEn = farmCrop.getCrop().getNameEn();
        this.cropNameMr = farmCrop.getCrop().getNameMr();
        this.sowingDate = farmCrop.getSowingDate();
        this.expectedHarvestDate = farmCrop.getExpectedHarvestDate();
        this.status = farmCrop.getStatus();
    }
}