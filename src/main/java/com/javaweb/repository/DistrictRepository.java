package com.javaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.entity.DistrictEntity;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
//	DistrictEntity findNameById(Long id);
}
