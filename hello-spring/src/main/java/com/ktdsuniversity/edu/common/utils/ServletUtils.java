package com.ktdsuniversity.edu.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class ServletUtils {

	// 세션을 직접 가져오기 위해서 씀
	private ServletUtils() {}
	
	public static HttpServletRequest getRequest() {
		return ServletUtils.getRequestAttributes().getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		return ServletUtils.getRequestAttributes().getResponse();
	}
	
	public static String getIp() {
		return ServletUtils.getRequest().getRemoteAddr();
	}
	
	private static ServletRequestAttributes getRequestAttributes() {
		return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes(); // Holder는 어떤 객체를 담고 있는 것이라고 생각하면 된다.
	}
	
}