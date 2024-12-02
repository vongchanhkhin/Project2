package com.javaweb.repository;

import java.util.List;

import com.javaweb.entity.RentAreaEntity;

public interface RentAreaRepository {
	List<RentAreaEntity> findValueByBuildingId(Long id);
}
