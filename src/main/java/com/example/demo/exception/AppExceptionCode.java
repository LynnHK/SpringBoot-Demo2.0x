package com.example.demo.exception;

public interface AppExceptionCode {
	
	/**
	 * 非法请求(通常用于请求方式错误)
	 */
	int ILLEGAL_METHOD = 500;

	/**
	 * 非法参数（通常用于参数检查）
	 */
	int ILLEGAL_ARGS = 501;
	
	/**
	 * 无效参数（通常用于获取不到数据）
	 */
	int INVALID_ARGS = 502;
	
}
