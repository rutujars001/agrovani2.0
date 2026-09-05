package com.agrovani.backend.repository;

import com.agrovani.backend.entity.FarmCrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmCropRepository extends JpaRepository<FarmCrop, Long> {

    List<FarmCrop> findByFarmId(Long farmId);

    List<FarmCrop> findByFarmFarmerIdAndStatus(Long farmerId, String status);
}