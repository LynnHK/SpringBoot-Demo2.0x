package com.example.demo.utils;

import java.util.UUID;

import org.springframework.util.DigestUtils;

public class DemoUtils {
	
	public static String getUniqueMd5() {
		String uuid = UUID.randomUUID().toString();
		return getMd5(uuid);
	}
	
	public static String getMd5(String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes());
	}
	
	public static void main(String[] args) {
		System.out.println(getUniqueMd5());
	}

}
