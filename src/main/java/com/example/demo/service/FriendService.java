package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.FriendInfo;

public interface FriendService {

	List<FriendInfo> getAll(String uin);
	
}
