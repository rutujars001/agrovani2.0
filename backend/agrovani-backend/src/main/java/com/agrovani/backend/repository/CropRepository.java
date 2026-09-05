package com.agrovani.backend.repository;

import com.agrovani.backend.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {

    Optional<Crop> findByCode(String code);

    List<Crop> findByIsActiveTrue();

    boolean existsByCode(String code);
}