package com.javaweb.utils;

public class NumberUtil {
	public static boolean isNum(String value) {
		try {
			Integer number = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
}
