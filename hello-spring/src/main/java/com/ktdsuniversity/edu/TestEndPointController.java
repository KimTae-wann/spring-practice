package com.ktdsuniversity.edu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * End Point 를 생성하는 역할.
 */

@Controller // 해당 클래스가 EndPoint 를 만들 수 있도록 지원
public class TestEndPointController {
	
	// Spring Application이 시작될 때
	// @Controller가 적용된 모든 클래스를 찾아
	// 해당 클래스들을 인스턴스로 생성한다.
	// 생성된 인스턴스들은 Bean Container에 저장된다.
	
	
	// "/hello" EndPoint 생성
	@GetMapping("/hello")
	public ResponseEntity<String> viewHelloHtml() {
		// 사용자가 "/hello" EndPoint 를 요청할 경우, 사용자에게 보여줄 HTML 페이지 생성.
		
		// 사용자에게 보여줄 HTML 반환
		return new ResponseEntity<>("Hello!", HttpStatus.OK);
	}
	
	
	/*
	 * "/jsp" EndPoint
	 * --hellojsp.jsp 파일을 읽어서 HTML로 변환시킨 후 결과를 반환
	 */
	@GetMapping("/jsp")
	public String viewHelloJspPage(Model model) {
		// Model model parameter --> Template Engine(JSP)에게 데이터를 전송시키는 객체
		// ????
		// name이라는 키(변수명)로 "김태완" 할당해서 템플릿에게 전달
		model.addAttribute("name", "김태완");
		model.addAttribute("age", 30);
		
		
		// spring.mvc.view.prefix + hellojsp + spring.mvc.view.suffix
		// /WEB-INF/views/hellojsp/.jsp
		// Controller에서 String 타입을 반환시켰을 경우
		// prefix + 반환값 + suffix 의 경로를
		// /src/main/webapp 아래에서 탐색한다.

		// 탐색한 jsp 파일을 HTML로 변환시켜 브라우저에게 전달.
		return "hellojsp";
	}
	
	/*
	 * 사용자가 "/" 엔트포인트에 접근하면
	 * "첫 페이지입니다. 환영합니다"를 브라우저에 보내주는 코드 작성
	 */
	
}
