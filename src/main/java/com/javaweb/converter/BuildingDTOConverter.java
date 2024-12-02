package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.BuildingDTO;

@Component
public class BuildingDTOConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
		
		List<RentAreaEntity> areaEntities = buildingEntity.getRentAreaEntities();

		String rentAreaValue = areaEntities.stream().map(i -> i.getValue().toString())
				.collect(Collectors.joining(","));

		buildingDTO.setAddress(buildingEntity.getStreet() + ", " + buildingEntity.getWard() + ", " 
		+ buildingEntity.getDistrict().getName());
		
		buildingDTO.setRentArea(rentAreaValue);
		
		return buildingDTO;
	}
}
