package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.entity.RentAreaEntity;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.utils.JDBCConnectionUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> findValueByBuildingId(Integer id) {
		String sql = "SELECT * FROM rentarea WHERE rentarea.buildingid = " + id + ";";
		List<RentAreaEntity> areaEntities = new ArrayList<>();
		
		try (Connection conn = JDBCConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				RentAreaEntity areaEntity = new RentAreaEntity();
//				areaEntity.setId(rs.getInt("id"));
				areaEntity.setValue(rs.getInt("value"));
//				areaEntity.setBuildingId(rs.getInt("buildingid"));
				
				areaEntities.add(areaEntity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return areaEntities;
	}

}
