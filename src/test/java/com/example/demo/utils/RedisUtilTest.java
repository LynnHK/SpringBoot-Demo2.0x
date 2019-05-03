package com.example.demo.utils;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.example.demo.DemoApplicationTests;
import com.example.demo.entity.UserInfo;

public class RedisUtilTest extends DemoApplicationTests {
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Test
	public void test() throws InterruptedException {
		Date date = new Date();
		UserInfo user = UserInfo.builder().id(1).age(20).name("51dobe").phone("12345678901").pwd("asdfghjkl").createTime(date).updateTime(date).build();
		String userStr = JSON.toJSONString(user);
		
		redisUtil.set("user:data:info", userStr, 60);
		redisUtil.set("user:data:info", userStr);
		Thread.sleep(2 * 1000);
		
		long expire = redisUtil.getExpire("user:data:info");
		System.out.println("expire:" + expire + "s");
		
		userStr = redisUtil.get("user:data:info");
		System.out.println(userStr);
		System.out.println(JSON.parseObject(userStr, UserInfo.class));
	}
	
//	@Test
	public void test2() throws InterruptedException {
		Date date = new Date();
		UserInfo user = UserInfo.builder().id(1).age(20).name("51dobe").phone("12345678901").pwd("asdfghjkl").createTime(date).updateTime(date).build();
		String userStr = JSON.toJSONString(user);
		
		redisUtil.set("user:data:info", userStr, 30);
//		redisUtil.set("user:data:info", userStr);
//		Thread.sleep(2 * 1000);
		
//		long expire = redisUtil.getExpire("user:data:info");
//		System.out.println("expire:" + expire + "s");
		
//		userStr = redisUtil.get("user:data:info");
//		System.out.println(userStr);
//		System.out.println(JSON.parseObject(userStr, User.class));

//		Jedis jedis = new Jedis("139.196.102.196"); 
//		jedis.auth("Lohk200801");
//		RedisMsgPubSubListener listener = new RedisMsgPubSubListener(); 
//		jedis.psubscribe(listener, "__key*__:*");
//		jedis.close();
		
	}
	
}
