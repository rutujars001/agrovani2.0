package com.agrovani.backend.service;

import com.agrovani.backend.dto.FarmerRegistrationRequest;
import com.agrovani.backend.dto.FarmerResponse;
import com.agrovani.backend.entity.Farmer;
import com.agrovani.backend.exception.FarmerNotFoundException;
import com.agrovani.backend.exception.DuplicatePhoneNumberException;
import com.agrovani.backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerService(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Transactional
    public FarmerResponse register(FarmerRegistrationRequest request) {
        if (farmerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicatePhoneNumberException(
                    "A farmer is already registered with phone number " + request.getPhoneNumber());
        }

        Farmer farmer = new Farmer();
        farmer.setFullName(request.getFullName().trim());
        farmer.setPhoneNumber(request.getPhoneNumber());
        farmer.setVillage(request.getVillage());
        farmer.setTaluka(request.getTaluka());
        farmer.setDistrict(request.getDistrict() != null ? request.getDistrict() : "Solapur");
        farmer.setPreferredLanguage(
                request.getPreferredLanguage() != null ? request.getPreferredLanguage() : "mr");

        return new FarmerResponse(farmerRepository.save(farmer));
    }

    @Transactional(readOnly = true)
    public FarmerResponse getByPhoneNumber(String phoneNumber) {
        Farmer farmer = farmerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new FarmerNotFoundException(
                        "No farmer found with phone number " + phoneNumber));
        return new FarmerResponse(farmer);
    }

    @Transactional(readOnly = true)
    public List<FarmerResponse> getAll() {
        return farmerRepository.findAll().stream()
                .map(FarmerResponse::new)
                .toList();
    }
}