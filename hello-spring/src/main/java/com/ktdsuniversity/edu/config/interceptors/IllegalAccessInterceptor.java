package com.ktdsuniversity.edu.config.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Endpoint에 접근하기 전, 세션이 존재할 경우
 * 컨트롤러 실행되지 않고 게시글 목록 조회 페이지로 이동 시킨다.
 */
public class IllegalAccessInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("__LOGIN_DATA__") == null) {
			response.sendRedirect("/"); // sendRedirect는 URL을 파라미터 값으로 전달한다.
			return false;
		}
		return true;
	}
}