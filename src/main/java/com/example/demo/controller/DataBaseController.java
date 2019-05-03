package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.APIResponse;
import com.example.demo.entity.FriendInfo;
import com.example.demo.service.FriendService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/database")
@RestController
public class DataBaseController {
	
	@Autowired
	private FriendService friendService;
	
	@GetMapping("/get_all_friend")
	public Object getAllFriend(String uin) {
		log.info("[get_all_friend] uin={}", uin);
		
		List<FriendInfo> friends = friendService.getAll(uin);
		
		APIResponse response = null;
		if (friends == null) {
			response = APIResponse.builder().code(APIResponse.FAIL).msg("failed").build();
		} else {
			response = APIResponse.builder().code(APIResponse.SUCCESS).msg("success").data(friends).build();
		}
		
		log.info("[get_all_friend] resultcode={}, resultmsg={}", response.getCode(), response.getMsg());
		return response;
	}
	
}
