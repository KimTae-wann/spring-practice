package com.ktdsuniversity.edu.common.utils;

public abstract class ObjectUtils {

	// 객체가 비었는지 확인하기 위해서
	private ObjectUtils() {}
	
	public static boolean isNull(Object ... object) {
		for (Object obj : object) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotNull(Object ... object) {
		for (Object obj : object) {
			if (obj == null) {
				return false;
			}
		}
		return true;
	}
	
}