package com.javaweb.custom.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.javaweb.custom.annotation.ColumnCustom;
import com.javaweb.custom.annotation.EntityCustom;

public class ResultSetMapper<T> {
	public List<T> mapRow(ResultSet rs, Class<T> tClass) {
		List<T> results = new ArrayList<>();

		try {
			if (tClass.isAnnotationPresent(EntityCustom.class)) {
				// lay ten cac column ma rs tra ve
				ResultSetMetaData data = rs.getMetaData();
				Field[] fields = tClass.getDeclaredFields();

				while (rs.next()) {
					T object = tClass.newInstance();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						String columnName = data.getColumnName(i);
						Object columnValue = rs.getObject(i);

						for (Field field : fields) {
							field.setAccessible(true);
							if (field.isAnnotationPresent(ColumnCustom.class)) {
								ColumnCustom columnCustom = field.getAnnotation(ColumnCustom.class);
								if (columnCustom.name().equals(columnName)) {
									BeanUtils.setProperty(object, field.getName(), columnValue);
									break;
								}
							}
						}
					}
					results.add(object);
				}

			}
			return results;
		} catch (Exception e) {
			return null;
		}

	}
}
