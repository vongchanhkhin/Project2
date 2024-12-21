package com.javaweb.custom;

import java.util.List;

public interface JDBCRepository<T> {
	List<T> findAll();
}
