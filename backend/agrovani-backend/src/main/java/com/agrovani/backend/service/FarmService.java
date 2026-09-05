package com.agrovani.backend.service;

import com.agrovani.backend.dto.FarmCropRequest;
import com.agrovani.backend.dto.FarmCropResponse;
import com.agrovani.backend.dto.FarmRequest;
import com.agrovani.backend.dto.FarmResponse;
import com.agrovani.backend.entity.Crop;
import com.agrovani.backend.entity.Farm;
import com.agrovani.backend.entity.FarmCrop;
import com.agrovani.backend.entity.Farmer;
import com.agrovani.backend.exception.CropNotFoundException;
import com.agrovani.backend.exception.DuplicateResourceException;
import com.agrovani.backend.exception.FarmNotFoundException;
import com.agrovani.backend.exception.FarmerNotFoundException;
import com.agrovani.backend.repository.CropRepository;
import com.agrovani.backend.repository.FarmCropRepository;
import com.agrovani.backend.repository.FarmRepository;
import com.agrovani.backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final CropRepository cropRepository;
    private final FarmCropRepository farmCropRepository;

    public FarmService(FarmRepository farmRepository,
                       FarmerRepository farmerRepository,
                       CropRepository cropRepository,
                       FarmCropRepository farmCropRepository) {
        this.farmRepository = farmRepository;
        this.farmerRepository = farmerRepository;
        this.cropRepository = cropRepository;
        this.farmCropRepository = farmCropRepository;
    }

    @Transactional
    public FarmResponse addFarm(Long farmerId, FarmRequest request) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new FarmerNotFoundException("No farmer found with id " + farmerId));

        Farm farm = new Farm();
        farm.setFarmer(farmer);
        farm.setLabel(request.getLabel());
        farm.setAreaAcres(request.getAreaAcres());
        farm.setVillage(request.getVillage() != null ? request.getVillage() : farmer.getVillage());
        farm.setLatitude(request.getLatitude());
        farm.setLongitude(request.getLongitude());
        farm.setSoilType(request.getSoilType());
        farm.setIrrigationType(request.getIrrigationType());

        return new FarmResponse(farmRepository.save(farm));
    }

    @Transactional(readOnly = true)
    public List<FarmResponse> getFarmsForFarmer(Long farmerId) {
        if (!farmerRepository.existsById(farmerId)) {
            throw new FarmerNotFoundException("No farmer found with id " + farmerId);
        }
        return farmRepository.findByFarmerId(farmerId).stream()
                .map(FarmResponse::new)
                .toList();
    }

    @Transactional
    public FarmCropResponse addCropToFarm(Long farmId, FarmCropRequest request) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new FarmNotFoundException("No farm found with id " + farmId));

        String code = request.getCropCode().trim().toUpperCase();
        Crop crop = cropRepository.findByCode(code)
                .orElseThrow(() -> new CropNotFoundException("No crop found with code " + code));

        boolean alreadyActive = farmCropRepository.findByFarmId(farmId).stream()
                .anyMatch(fc -> fc.getCrop().getId().equals(crop.getId())
                        && "ACTIVE".equals(fc.getStatus()));

        if (alreadyActive) {
            throw new DuplicateResourceException(
                    crop.getNameEn() + " is already an active crop on this farm");
        }

        FarmCrop farmCrop = new FarmCrop();
        farmCrop.setFarm(farm);
        farmCrop.setCrop(crop);
        farmCrop.setSowingDate(request.getSowingDate());
        farmCrop.setExpectedHarvestDate(request.getExpectedHarvestDate());

        return new FarmCropResponse(farmCropRepository.save(farmCrop));
    }

    @Transactional(readOnly = true)
    public List<FarmCropResponse> getCropsForFarm(Long farmId) {
        if (!farmRepository.existsById(farmId)) {
            throw new FarmNotFoundException("No farm found with id " + farmId);
        }
        return farmCropRepository.findByFarmId(farmId).stream()
                .map(FarmCropResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FarmCropResponse> getActiveCropsForFarmer(Long farmerId) {
        if (!farmerRepository.existsById(farmerId)) {
            throw new FarmerNotFoundException("No farmer found with id " + farmerId);
        }
        return farmCropRepository.findByFarmFarmerIdAndStatus(farmerId, "ACTIVE").stream()
                .map(FarmCropResponse::new)
                .toList();
    }
}