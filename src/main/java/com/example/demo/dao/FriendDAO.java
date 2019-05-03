package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.FriendInfo;

public interface FriendDAO {

	public List<FriendInfo> getAll(String uin);
	
}
