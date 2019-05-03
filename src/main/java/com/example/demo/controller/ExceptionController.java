package com.example.demo.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.AppException;
import com.example.demo.exception.AppExceptionCode;

@RequestMapping("/exception")
@RestController
public class ExceptionController {
	
	/**
	 * 1.测试get请求缺失必须参数
	 * 2.测试未知运行时异常
	 * @param str
	 */
	@GetMapping("/m1")
	public void m1(@RequestParam String str) {
		int a = 0;
		System.out.println(1 / a);
	}
	
	/**
	 * 1.测试请求方式不对异常
	 * 2.测试抛出的自定义异常
	 */
	@PostMapping("/m2")
	public void m2(@RequestBody UserInfo params) {
		if (StringUtils.isEmpty(params.getName())) {
			throw new AppException(AppExceptionCode.ILLEGAL_ARGS, "Required args 'name' is missing");
		}
		System.out.println(params);
	}

}
