package com.ktdsuniversity.edu.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.exceptions.HelloSpringException;


/**
 * Spring Application에서 던져져 catch되지 않은
 * 예외들을 처리하는 클래스
 * 
 * @Controller와 유사한 형태
 * 	==> URL이 엔드포인트
 * 
 * @ControllerAdvice
 * 	==> Exception이 엔드포인트
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
	 * HellpSpringException이 던져지면
	 * viewErrorPage가 실행된다
	 * 실행된 결과는 ModelAndView가 된다
	 * 
	 * @return 사용자에게 보여줄 템플릿의 이름
	 */

	@ExceptionHandler(HelloSpringException.class)
	public String viewErrorPage(HelloSpringException hse, Model model) {
		String errorPage = hse.getErrorPage();
		
		String message = hse.getMessage();
		model.addAttribute("errorMessage", message);

		Object modelData = hse.getObject();
		if (modelData != null) {
			model.addAttribute("errorData", modelData);
		}
		
		return errorPage;
	}
	
	@ResponseBody
	@ExceptionHandler(HelloSpringApiException.class)
	public Map<String, Object> returnErrorJson(HelloSpringApiException hsae) {
		logger.error(hsae.getMessage(), hsae);
		
		int status = hsae.getErrorStatus();
		Object errorObject = hsae.getError();
		
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("status", status);
		responseData.put("error", errorObject);
		
		return responseData;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String viewErrorPage(RuntimeException Re) {
		logger.error(Re.getMessage(), Re);
		return "errors/500";
	}
}
