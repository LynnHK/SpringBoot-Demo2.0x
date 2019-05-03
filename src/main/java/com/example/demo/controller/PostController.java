package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


@Api(value = "POST接口请求")
@Slf4j
@RequestMapping("/post")
@RestController
public class PostController {
	
	@ApiOperation(value = "测试1", notes = "restful标准返回")
	@PostMapping("/m1")
	public Object m1(@ApiParam(name = "params", value = "JSON参数") @RequestBody String params) {
		log.info("req1 params={}", params);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", params);
		
		log.info("res1={}", result);
		return result;
	}

	@ApiOperation(value = "测试2", notes = "restful标准返回")
	@ApiImplicitParam(paramType = "body",name= "params" ,value = "用户信息的json", dataType = "json")
	@PostMapping("/m2")
	public Object m2(@RequestBody Map<String, Object> params) {
		log.info("req2 params={}", params);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", params);
		
		log.info("res2={}", result);
		return result;
	}
	
	@ApiOperation(value = "测试3", notes = "restful标准返回")
	@ApiModelProperty(value="params",notes = "用户信息的JSON串")
	@PostMapping("/m3")
	public Object m3(@RequestBody UserInfo params) {
		log.info("req3 params={}", params);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", params);
		
		log.info("res3={}", result);
		return result;
	}
	
}
