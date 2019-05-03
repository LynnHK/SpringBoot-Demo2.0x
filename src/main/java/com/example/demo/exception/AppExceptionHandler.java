package com.example.demo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.entity.APIResponse;
import com.example.demo.utils.JSONUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
	
	/**
	 * 全局异常处理
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public Object expHandler(Exception ex, HttpServletRequest req) {
//		ModelAndView model = new ModelAndView();
		String msg = ex.getMessage();
		
		if(null != msg && msg.contains("not supported")) {
//			model.addObject("code", APIResponse.FAIL);
//			model.addObject("message", msg);
//			model.setViewName("404.html");
			APIResponse response = APIResponse.builder().code(AppExceptionCode.ILLEGAL_METHOD).msg(msg).build();
			log.info("Request for '{}' results: {}", req.getRequestURI(), JSONUtil.toJSONString(response));
			return response;
		}
		
		if (null != msg && msg.contains("is not present")) {
//			model.addObject("code", APIResponse.FAIL);
//			model.addObject("message", msg);
//			model.setViewName("404.html");
			return APIResponse.builder().code(AppExceptionCode.ILLEGAL_ARGS).msg(msg).build();
		}
		
		 
//		model.addObject("code", APIResponse.SYS_ERROR);
//		model.addObject("message", "system error");
//		model.setViewName("error.html");
		log.error("system error: ", ex);
		msg = "An exception has occurred here. Please contact the service provider.";
		return APIResponse.builder().code(APIResponse.SYS_ERROR).msg(msg).build();
	}
	
	/**
	 * 自定义异常处理
	 * @param ex
	 * @param req
	 * @return
	 */
	@ExceptionHandler(value = AppException.class)
	public Object appExpHandler(AppException ex, HttpServletRequest req) {
		
		return APIResponse.builder().code(ex.getCode()).msg(ex.getMessage()).build();
	}
	
}
