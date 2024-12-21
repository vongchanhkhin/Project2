package com.javaweb.custom.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.javaweb.custom.JDBCRepository;
import com.javaweb.custom.annotation.TableCustom;
import com.javaweb.utils.JDBCConnectionUtil;

public class JDBCRepositoryDefinition<T> implements JDBCRepository<T> {

	@Override
	public List<T> findAll() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<T> result = new ArrayList<>();
		String tableName = "";
		if (tClass.isAnnotationPresent(TableCustom.class)) {
			TableCustom tableCustom = tClass.getAnnotation(TableCustom.class);
			tableName = tableCustom.name();
		}
		String sql = "SELECT * FROM " + tableName;

		try (Connection conn = JDBCConnectionUtil.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

		} catch (SQLException ex) {

		}

		return null;
	}

}
