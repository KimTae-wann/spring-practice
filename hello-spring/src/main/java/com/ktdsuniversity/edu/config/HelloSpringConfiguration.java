package com.ktdsuniversity.edu.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.config.interceptors.SessionInterceptor;

// applicaiton.yml에서 작성할 수 없는 설정들을 적용하기 위한 Annotation
// @Component의 자식 Annotation
@Configuration
// spring-boot-starter-validation 동작 활성화 시키기
// @EnableWebMvc가 추가되면 application.yml의 mvc 관련 설정들이 모두 무시된다.
//		1. spring.mvc.view.prefix, spring.mvc.view.suffix
//		2. src/main/resources/static 경로 사용 불가능
@EnableWebMvc
public class HelloSpringConfiguration implements WebMvcConfigurer {
	// Interceptor 등록 및 대상 URL 지정.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SessionInterceptor sessionInterceptor = new SessionInterceptor();
		
		List<String> excludePatterns = new ArrayList<>();
		
		excludePatterns.add("/regist"); // 회원 가입 페이지 & 처리
		excludePatterns.add("/regist/check/duplicate/**"); // 회원가입 이메일 중복 검사
		excludePatterns.add("/login"); // 로그인 페이지 & 처리
		excludePatterns.add("/js/**"); // static resources
		excludePatterns.add("/css/**"); // static resources
		excludePatterns.add("/image/**"); // static resources
		excludePatterns.add("/"); // 게시글 목록 조회
		excludePatterns.add("/view/**"); // 게시글 내용 조회
		excludePatterns.add("/file/**"); // 파일 다운로드
		excludePatterns.add("/error"); // 에러 내용이 보여지는 페이지
		
		
		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns("/**") // 해당 URL을 대상으로 Session Check를 수행.
				.excludePathPatterns(excludePatterns) // sessionInterceptor가 적용되지 않을 URL 명시
				;
		
		// 인터셉터를 여러개 달아 줄 경우 순차적으로 실행된다.
	}
	
	
	// WebMvc 설정을 위한 Configuration
	// @EnableWebMvc Annotation 에서 적용하는 기본 설정들을 변경하기 위함
	
	// configureViewResolvers 설정
	// spring.view.prefix, spring.mvc.view.suffix 재설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
		// 여기까지만 하면 css나 js 이런것을 못받아 온다
		// 즉 static을 못가져온다. --> 이미지도 못받아온다. 아래에서 설정하면됨
	}
	
	// addResourceHandlers
	// src/main/resources/static 경로의 EndPoint 재설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /static/css/ 폴더에 있는 파일들에 대한 EndPoint 설정.
		registry.addResourceHandler("/css/**") // /static/css/ 의 엔드포인트
				.addResourceLocations("classpath:/static/css/");  // /static/css/ 의 물리적인 위치
		// /static/image/ 폴더에 있는 파일들에 대한 EndPoint 설정
		registry.addResourceHandler("/image/**") // /static/image/ 의 엔드포인트
				.addResourceLocations("classpath:/static/image/"); // /static/image/ 의 물리적인 위치
		// /static/js/ 폴더에 있는 파일들에 대한 EndPoint 설정
		registry.addResourceHandler("/js/**") // /static/js/ 의 엔드포인트
				.addResourceLocations("classpath:/static/js/"); // /static/js/ 의 물리적인 위치
	}
}
