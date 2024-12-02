package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.JDBCConnectionUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();

		if (staffId != null) {
			sql.append("JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}

		List<String> typeCodes = buildingSearchBuilder.getTypeCodes();
		if (typeCodes != null && typeCodes.size() != 0) {
			sql.append("JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append("JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
	}

	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {

		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();

			for (Field f : fields) {
				f.setAccessible(true);
				String fieldName = f.getName();

				if (!fieldName.equals("staffId") && !fieldName.equals("typeCodes") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {

					Object value = f.get(buildingSearchBuilder);

					if (value != null) {
						if (f.getType().getName().equals("java.lang.Integer")
								|| f.getType().getName().equals("java.lang.Long")
								|| f.getType().getName().equals("java.lang.Float")) {

							where.append("AND b." + fieldName.toLowerCase() + " = " + value + " ");

						} else if (f.getType().getName().equals("java.lang.String")) {

							where.append("AND b." + fieldName.toLowerCase() + " LIKE '%" + value + "%' ");

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append("AND assignmentbuilding.staffId = " + staffId + " ");
		}

		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		if (rentAreaFrom != null || rentAreaTo != null) {
			where.append("AND EXISTS (SELECT * FROM rentarea WHERE rentarea.buildingid = b.id ");

			if (rentAreaFrom != null) {
				where.append("AND rentarea.value >= " + rentAreaFrom + " ");
			}

			if (rentAreaTo != null) {
				where.append("AND rentarea.value <= " + rentAreaTo);
			}

			where.append(") ");
		}

		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		if (rentPriceFrom != null || rentPriceTo != null) {
			if (rentPriceFrom != null) {
				where.append("AND b.rentprice >= " + rentPriceFrom + " ");
			}

			if (rentPriceTo != null) {
				where.append("AND b.rentprice <= " + rentPriceTo + " ");
			}
		}

		// java 7
//		if (typeCodes != null && typeCodes.size() != 0) {
//			List<String> temp = new ArrayList<>();
//			
//			for (String s : typeCodes) {
//				temp.add("'" + s + "'");
//			}
//			
//			where.append("AND renttype.code IN (" + String.join(",", temp) + ") ");
//		}

		// java 8
		List<String> typeCodes = buildingSearchBuilder.getTypeCodes();
		if (typeCodes != null && typeCodes.size() != 0) {
			where.append("AND (");
			String str = typeCodes.stream().map(i -> "renttype.code LIKE '%" + i + "%'")
					.collect(Collectors.joining(" OR "));
			where.append(str + ") ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {

		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.street, b.ward, b.districtid, "
				+ "b.rentprice, b.floorarea, b.managername, b.managerphonenumber, b.servicefee, "
				+ "b.brokeragefee FROM building b ");

		joinTable(buildingSearchBuilder, sql);

		StringBuilder where = new StringBuilder("where 1 = 1 ");

		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);

		where.append("GROUP BY b.id;");

		sql.append(where);

		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		
		return query.getResultList();
	}

}
