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

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.JDBCConnectionUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Integer staffId = buildingSearchBuilder.getStaffId();

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
		Integer staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			where.append("AND assignmentbuilding.staffId = " + staffId + " ");
		}

		Integer rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Integer rentAreaTo = buildingSearchBuilder.getAreaTo();
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

		Integer rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Integer rentPriceTo = buildingSearchBuilder.getRentPriceTo();
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

		List<BuildingEntity> result = new ArrayList<>();

		try (Connection conn = JDBCConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {

			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getInt("b.id"));
				buildingEntity.setName(rs.getString("b.name"));
				buildingEntity.setStreet(rs.getString("b.street"));
				buildingEntity.setWard(rs.getString("b.ward"));
				buildingEntity.setDistricId(rs.getInt("b.districtid"));
				buildingEntity.setManagerName(rs.getString("b.managername"));
				buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
				buildingEntity.setFloorArea(rs.getInt("b.floorarea"));
				buildingEntity.setRentPrice(rs.getInt("b.rentprice"));
				buildingEntity.setServiceFee(rs.getString("b.servicefee"));
				buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));

				result.add(buildingEntity);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}

}
