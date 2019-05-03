package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/get")
@RestController
public class GetController {

	@GetMapping("/m1")
	public Object m1(@RequestParam String uid) {
		log.info("req1 uid={}", uid);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", uid);
		
		log.info("res1={}", result);
		return result;
	}
	
	@GetMapping("/m2/{depid}/{uid}")
	public Object m2(@PathVariable String depid, @PathVariable String uid) {
		log.info("req2 depid={}, uid={}", depid, uid);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", uid);
		
		log.info("res2={}", result);
		return result;
	}
	
	@GetMapping("/m3")
	public Object m3(UserInfo params) {
		log.info("req3 params={}", params);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "200");
		result.put("message", "success");
		result.put("data", params);
		
		log.info("res3={}", result);
		return result;
	}
	
}
