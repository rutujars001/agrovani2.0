package com.agrovani.backend.dto;

import com.agrovani.backend.entity.Farmer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FarmerResponse {

    private final Long id;
    private final String fullName;
    private final String phoneNumber;
    private final String village;
    private final String taluka;
    private final String district;
    private final String preferredLanguage;
    private final LocalDateTime createdAt;

    public FarmerResponse(Farmer farmer) {
        this.id = farmer.getId();
        this.fullName = farmer.getFullName();
        this.phoneNumber = farmer.getPhoneNumber();
        this.village = farmer.getVillage();
        this.taluka = farmer.getTaluka();
        this.district = farmer.getDistrict();
        this.preferredLanguage = farmer.getPreferredLanguage();
        this.createdAt = farmer.getCreatedAt();
    }
}