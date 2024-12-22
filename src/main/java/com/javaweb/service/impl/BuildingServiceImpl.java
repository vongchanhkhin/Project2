package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.custom.entity.BuildingJDBCEntity;
import com.javaweb.custom.impl.BuildingJDBCRepositoryImpl;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingConverter buildingDTOConverter;

	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BuildingJDBCRepositoryImpl buildingJDBCRepositoryImpl = new BuildingJDBCRepositoryImpl();

	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCodes) {
		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params,
				typeCodes);

		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<>();

		for (BuildingEntity building : buildingEntities) {
			BuildingDTO buildingDTO = buildingDTOConverter.toBuildingDTO(building);

			result.add(buildingDTO);
		}

		return result;
	}

	@Override
	public List<BuildingJDBCEntity> findAll() {
		List<BuildingJDBCEntity> results = buildingJDBCRepositoryImpl.findAll();
		
		return results;
	}

	@Override
	public void save(BuildingRequestDTO buildingRequestDTO) {
		BuildingJDBCEntity buildingJDBCEntity = modelMapper.map(buildingRequestDTO, BuildingJDBCEntity.class);
		
		buildingJDBCRepositoryImpl.save(buildingJDBCEntity);
		
	}

}
