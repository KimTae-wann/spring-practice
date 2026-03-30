package com.ktdsuniversity.edu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculateController {

	/*
	 * <pre>
	 * 엔드 포인트 = /calc
	 * 반환시키는 템플릿 이름 = calc.jsp
	 * 템플릿으로 반환 시키는 데이터
	 * - 1. fristNum
	 * - 2. secondNum
	 * - 3. result (firstNum + secondNum 의 결과)
	 * 템플릿의 결과
	 * 	<div>
	 * 		<span>firstNum</span>
	 * 		<span>secondNum</span>
	 * 		<span>result</span>
	 * 	</div>
	 */
	@GetMapping("/calc")
	public String viewCalcPage(Model model) {
 		int firstNum = 1;
		int secondNum = 2;
		
		model.addAttribute("firstNum", firstNum);
		model.addAttribute("secondNum", secondNum);
		model.addAttribute("result", (int)model.getAttribute("firstNum") + (int)model.getAttribute("secondNum"));

		return "calc";
	}
	
	/*
	 * <pre>
	 * Browser 에서 EndPoint로 파라미터를 보내는 3가지 방법.
	 * 		1. Query String Parameter --> EndPoint뒤에 "?"를 이용해 보내는 방법
	 * 		ex) 1.	/ EndPoint?key=value&key2=value2&....
	 * 			2.	/calc2?firstNum=3&secondNum=10
	 * 
	 * 		2. Form Parameter --> <form></form? 를 이용해서 보내는 방법
	 * 		ex) 1.	<form action="/EndPoint"></form>
	 * 					<input name="key" value="value" /> 
	 * 					<select name="key">
	 * 						<option value="value2">Text</option>
	 * 						<option value="value3">Other Text</option>
	 * 					</select> 
	 * 					<textarea> </textarea>
	 * 				</form>
	 * 
	 * 		3. Request Body --> http Request Body 영역에 파라미터를 보내는 방법
	 * 		ex) 파일 업로드, AJAX 요청
	 * 
	 * 		4. hybrid (Alternative - Complex)
	 * 			-> Query String Parameter + Form Parameter
	 * 			-> Query String Parameter + Request Body
	 * 
	 * 		* Spring 전용 Parameter
	 * 			-> Path(URL) Variable
	 * 				Query String parameter 외의 파라미터를 URL로 보내는 방법
	 * 		
	 * Spring EndPoint 에서 파라미터를 받아오는 4가지 방법
	 * 		1. HttpServletRequest 객체를 이용하는 방법 (Spring 에서는 잘 사용되지 않는다)
	 * 			-> Query String Parameter, Form Parameter, Request Body
	 * 			-> HTTP Header 정보 취득 가능. 요청자(브라우저)의 IP 취득 가능
	 * 				-> EndPoint를 호출한 위치(Referrer) 취득 가능
	 * 
	 * 		2. @RequestParam 을 이용하는 방법 (종종 사용 - 파라미터의 개수가 적을 때 - 2개 이하)
	 * 			-> Query String Parameter, Form Parameter 둘 중 하나 선택.
	 * 		
	 * 		3. @ModelAttribute를 이용하는 방법 (가장 많이 사용됨 - 파라미터의 개수가 많을 때)
	 * 			-> ModelAttribute는 Command Object 라고도 부른다.
	 * 			-> @ModelAttribute 애노테이션은 생략 가능.
	 * 			-> Query String Parameter, Form Parameter 를 받아올 수 있다
	 * 
	 * 		4. @RequestBody 를 이용하는 방법 (API-AJAX 기반의 프로젝트에서 주로 사용됨)
	 * 			-> Request Body 취득
	 * 		
	 * 		*. @PathVariable 를 이용하는 방법 (가장 많이 사용됨 - Path(URL) Variable 취득)
	 * </pre>
	 * @return
	 */
	
	// /calc2?f=1&s=3
	@GetMapping("/calc2")
	// required = false 면 값을 무조건 전달해야 한다. 전달하지 않으면 error. default는 true
	public String viewParamCalcPage(
			@RequestParam(required = false, defaultValue = "0") int f, 
			@RequestParam(required = false, defaultValue = "0") int s, 
			Model model) {
		
		int result = f + s;
		
		
		model.addAttribute("f", f);
		model.addAttribute("s", s);
		model.addAttribute("result", result);
		
		return "calc";
	}
}
