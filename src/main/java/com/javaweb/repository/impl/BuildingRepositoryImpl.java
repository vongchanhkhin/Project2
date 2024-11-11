package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.JDBCConnectionUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinTable(Map<String, Object> params, List<String> typeCodes, StringBuilder sql) {
		String staffId = (String) params.get("staffId");

		if (StringUtil.checkString(staffId)) {
			sql.append("JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}

		if (typeCodes != null && typeCodes.size() != 0) {
			sql.append("JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append("JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
	}

	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> item : params.entrySet()) {
			if (!item.getKey().equals("staffId") && !item.getKey().equals("typeCode")
					&& !item.getKey().startsWith("area") && !item.getKey().startsWith("rentPrice")) {
				String value = item.getValue().toString();

				if (StringUtil.checkString(value)) {
					if (NumberUtil.isNum(value)) {
						where.append("AND b." + item.getKey() + " = " + value + " ");
					} else {
						where.append("AND b." + item.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
		}
	}

	public static void querySpecial(Map<String, Object> params, List<String> typeCodes, StringBuilder where) {
		String staffId = (String) params.get("StaffId");
		if (StringUtil.checkString(staffId)) {
			where.append("AND assignmentbuilding.staffId = " + staffId + " ");
		}

		String rentAreaFrom = (String) params.get("areaFrom");
		String rentAreaTo = (String) params.get("areaTo");
		if (StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			where.append("AND EXISTS (SELECT * FROM rentarea WHERE rentarea.buildingid = b.id ");

			if (StringUtil.checkString(rentAreaFrom)) {
				where.append("AND rentarea.value >= " + rentAreaFrom + " ");
			}

			if (StringUtil.checkString(rentAreaTo)) {
				where.append("AND rentarea.value <= " + rentAreaTo);
			}

			where.append(") ");
		}

		String rentPriceFrom = (String) params.get("rentPriceFrom");
		String rentPriceTo = (String) params.get("rentPriceTo");
		if (StringUtil.checkString(rentPriceFrom) || StringUtil.checkString(rentPriceTo)) {
			if (StringUtil.checkString(rentPriceFrom)) {
				where.append("AND b.value >= " + rentAreaFrom + " ");
			}

			if (StringUtil.checkString(rentPriceTo)) {
				where.append("AND b.value <= " + rentAreaTo + " ");
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
		if (typeCodes != null && typeCodes.size() != 0) {
			where.append("AND (");
			String str = typeCodes.stream().map(i -> "renttype.code LIKE '%" + i + "%'")
					.collect(Collectors.joining(" OR "));
			where.append(str + ") ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCodes) {

		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.street, b.ward, b.districtid, "
				+ "b.rentprice, b.floorarea, b.managername, b.managerphonenumber, b.servicefee, "
				+ "b.brokeragefee FROM building b ");

		joinTable(params, typeCodes, sql);

		StringBuilder where = new StringBuilder("where 1 = 1 ");

		queryNormal(params, where);
		querySpecial(params, typeCodes, where);

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
