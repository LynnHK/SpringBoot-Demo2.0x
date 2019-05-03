package com.example.demo.utils;

import java.io.File;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class MethodTest {
	
//	@Test
	public void m1() {
		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isEmpty(""));
		System.out.println(StringUtils.isEmpty(" "));
		System.out.println(StringUtils.isEmpty("null"));
	}
	
	@Test
	public void m2() {
		File file = new File("xxx");
		System.out.println(file);
	}

}
