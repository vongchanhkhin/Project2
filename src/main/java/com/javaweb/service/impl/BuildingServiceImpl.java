package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingDTOConverter buildingDTOConverter;

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCodes) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCodes);
		List<BuildingDTO> result = new ArrayList<>();

		for (BuildingEntity building : buildingEntities) {
			BuildingDTO buildingDTO = buildingDTOConverter.toBuildingDTO(building);

			result.add(buildingDTO);
		}

		return result;
	}

}
