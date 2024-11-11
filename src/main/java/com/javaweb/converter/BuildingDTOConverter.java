package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.DistrictEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
		BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
		
		DistrictEntity districtEntity = districtRepository.findNameById(buildingEntity.getDistricId());
		List<RentAreaEntity> areaEntities = rentAreaRepository.findValueByBuildingId(buildingEntity.getId());

		String rentAreaValue = areaEntities.stream().map(i -> i.getValue().toString())
				.collect(Collectors.joining(","));

		buildingDTO.setAddress(buildingEntity.getStreet() + ", " + buildingEntity.getWard() + ", " + districtEntity.getName());
		buildingDTO.setRentArea(rentAreaValue);
		
		return buildingDTO;
	}
}
