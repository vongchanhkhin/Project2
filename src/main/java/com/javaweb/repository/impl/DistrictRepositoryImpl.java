package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.DistrictEntity;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USER = "root";
	static final String PASSWORD = "Vck031020";

	@Override
	public DistrictEntity findNameById(Integer id) {
		String sql = "SELECT d.name FROM district d WHERE d.id = " + id + ";";
		
		DistrictEntity districtEntity = new DistrictEntity();
		
		try (Connection conn = JDBCConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				districtEntity.setName(rs.getString("name"));
				
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return districtEntity;
	}

}
