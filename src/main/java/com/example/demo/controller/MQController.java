package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.DelayMissionType;
import com.example.demo.entity.APIResponse;
import com.example.demo.entity.DelayMission;
import com.example.demo.entity.UserInfo;
import com.example.demo.mq.service.MQService;
import com.example.demo.mq.service.MessageDelayQueue;
import com.example.demo.utils.DemoUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "RabbitMQ测试")
@RequestMapping("/mqtest")
@RestController
public class MQController {
	
	@Autowired
	private MQService mqService;
	
	@Autowired
	private MessageDelayQueue delayQueue;

	@ApiOperation(value = "测试1", notes = "点对点测试")
	@GetMapping("/m1")
	public Object m1(@ApiParam(name = "message", value = "需要发送的消息体", required = true) 
					 @RequestParam String message) {
		
		mqService.send(message);
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").build();
	}
	
	@ApiOperation(value = "测试2", notes = "广播测试-发布订阅")
	@GetMapping("/m2")
	public Object m2(@ApiParam(name = "message", value = "需要发送的消息体", required = true) 
					 @RequestParam String message) {
		
		mqService.topic(message);
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").build();
	}
	
	@ApiOperation(value = "测试3", notes = "广播测试-发布订阅-模糊匹配")
	@GetMapping("/m3")
	public Object m3(@ApiParam(name = "message", value = "需要发送的消息体", required = true) 
					 @RequestParam String message) {
		
		mqService.topic2(message);
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").build();
	}
	
	@ApiOperation(value = "测试4", notes = "广播测试-发布订阅")
	@GetMapping("/m4")
	public Object m4(@ApiParam(name = "message", value = "需要发送的消息体", required = true) 
					 @RequestParam String message) {
		
		mqService.topic3(message);
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").build();
	}
	
	@ApiOperation(value = "测试5", notes = "延时队列测试")
	@GetMapping("/m5")
	public Object m5(@ApiParam(name = "name", value = "姓名", required = true) 
					 @RequestParam String name) {
		for (int i = 0; i < 10; i++) {
			UserInfo userInfo = UserInfo.builder().id(i).name(name+i).age(i+10).build();
			
			DelayMission mission = DelayMission.builder().id(DemoUtils.getUniqueMd5())
					.type(DelayMissionType.ORDER.getType()).delayTime(i*10).createBy("system")
					.createTime(new Date()).data(userInfo).build();
			
			delayQueue.send(mission);
		}
		
		return APIResponse.builder().code(APIResponse.SUCCESS).msg("success").build();
	}
	
}
