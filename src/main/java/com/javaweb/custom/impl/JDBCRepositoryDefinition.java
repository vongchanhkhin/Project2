package com.javaweb.custom.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.javaweb.custom.JDBCRepository;
import com.javaweb.custom.annotation.ColumnCustom;
import com.javaweb.custom.annotation.TableCustom;
import com.javaweb.custom.mapper.ResultSetMapper;
import com.javaweb.utils.JDBCConnectionUtil;

public class JDBCRepositoryDefinition<T> implements JDBCRepository<T> {

	@Override
	public List<T> findAll() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<T> results = new ArrayList<>();

		String tableName = "";
		if (tClass.isAnnotationPresent(TableCustom.class)) {
			TableCustom tableCustom = tClass.getAnnotation(TableCustom.class);
			tableName = tableCustom.name();
		}

		String sql = "SELECT * FROM " + tableName;

		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		try (Connection conn = JDBCConnectionUtil.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			results = resultSetMapper.mapRow(rs, tClass);

			if (results != null) {
				return results;
			} else
				return new ArrayList<>();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
			return null;
		}
	}

	private String sqlInsert() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		List<T> results = new ArrayList<>();

		String tableName = "";
		if (tClass.isAnnotationPresent(TableCustom.class)) {
			TableCustom tableCustom = tClass.getAnnotation(TableCustom.class);
			tableName = tableCustom.name();
		}

		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		for (Field field : tClass.getDeclaredFields()) {
			if (fields.length() > 0) {
				fields.append(",");
				params.append(",");
			}
			if (field.isAnnotationPresent(ColumnCustom.class)) {
				field.setAccessible(true);
				ColumnCustom columnCustom = field.getAnnotation(ColumnCustom.class);
				fields.append(columnCustom.name());
				params.append("?");
			}
		}

		String sql = "INSERT INTO " + tableName + " (" + fields + ")" + " VALUES(" + params + ");";

		return sql;
	}

	@Override
	public void save(Object entity) {
		try (Connection conn = JDBCConnectionUtil.getConnection();
				PreparedStatement st = conn.prepareStatement(sqlInsert())) {
			
			Class<?> tClass = entity.getClass();
			Field[] fields = tClass.getDeclaredFields();
			int i = 1;
			for(Field field : fields) {
				field.setAccessible(true);
				try {
					st.setObject(i++, field.get(entity));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
			return;
		}
	}

}
