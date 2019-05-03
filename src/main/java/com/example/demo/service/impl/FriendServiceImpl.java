package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FriendDAO;
import com.example.demo.entity.FriendInfo;
import com.example.demo.service.FriendService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendDAO friendDAO;
	
	@Override
	public List<FriendInfo> getAll(String uin) {
		
		try {
			return friendDAO.getAll(uin);
		} catch (Exception e) {
			log.error("database error", e);
			return null;
		}
		
	}

}
