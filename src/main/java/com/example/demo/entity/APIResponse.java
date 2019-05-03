package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {

	public static final int SUCCESS = 200;
	public static final int FAIL = 500;
	public static final int SYS_ERROR = 9999;
	
	private int code;

	@JsonInclude(Include.NON_NULL)
	private String msg;
	
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
}
