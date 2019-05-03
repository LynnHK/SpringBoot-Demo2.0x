package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSON;
import com.example.demo.DemoApplicationTests;

public class PostControllerTest extends DemoApplicationTests {
	
	@Test
	public void testm() throws Exception {
		// 设置参数
		String uri = "/post/m3";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 1);
		params.put("name", "abc1234");
		params.put("age", 15);
		params.put("pwd", "12345678901");
		String content = JSON.toJSONString(params);
		
		// 创建请求
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri) // 请求方式及url
																	  .content(content) // 请求入参
																	  .contentType(MediaType.APPLICATION_JSON_UTF8); // 请求ContentType
		
		// 调用接口
		ResultActions action = this.mockMvc.perform(request);

		// 返回值预判
		String result = action.andExpect(MockMvcResultMatchers.status().isOk()) // 返回状态200
			  //.andDo(MockMvcResultHandlers.print()) // 打印接口请求和返回信息
			  //.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true)) // 对返回的具体数据进行判断
			  .andReturn().getResponse().getContentAsString(); // 获取接口返回值
		
		System.out.println(">>>>>>>返回result:" + result);
	}

}
