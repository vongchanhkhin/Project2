package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.customexception.FieldRequiredException;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.DistrictEntity;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.service.BuildingService;

@RestController
@Transactional
public class BuildingAPI {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCodes) {

		List<BuildingDTO> result = buildingService.findAll(params, typeCodes);

		return result;
	}
	
	@PostMapping(value = "/api/building")
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		
		DistrictEntity districtEntity = new DistrictEntity();
		
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		
		entityManager.persist(buildingEntity);
		System.out.print("ok");
		return;
	}
	
	@PutMapping(value = "/api/building")
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setId(1L);
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		
		DistrictEntity districtEntity = new DistrictEntity();
		
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		
		entityManager.merge(buildingEntity);
		System.out.print("ok");
		return;
	}
	
	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Long id) {
		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
		entityManager.remove(buildingEntity);
		System.out.print("ok");
	}
}
