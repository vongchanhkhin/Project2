package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USER = "root";
	static final String PASSWORD = "Vck031020";

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params) {
		String sql = "SELECT * FROM building b WHERE b.name LIKE '%" + params + "%'";
		List<BuildingEntity> result = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setNumberOfBasement(rs.getInt("numberofbasement"));
				buildingEntity.setWard(rs.getString("ward"));
				buildingEntity.setStreet(rs.getString("street"));

				result.add(buildingEntity);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}

}
