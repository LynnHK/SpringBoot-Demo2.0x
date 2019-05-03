package com.example.demo.exception;

/**
 * 自定义异常，抛出时可以指定异常信息，但抛出后只能修改其异常码，其他异常信息不得修改
 * @author lee
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 4989359437827815324L;
	
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public AppException() {
		super();
	}
	
	public AppException(String message) {
		super(message);
	}
	
	public AppException(Throwable cause) {
		super(cause);
	}
	
	public AppException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String toString() {
		return "AppException [code=" + code + ", message=" + super.getMessage() + "]";
	}
	
	public static void main(String[] args) {
		
		AppException exception = new AppException(1, "parameter not valid");
		exception.setCode(2);
		System.out.println(exception);
		
	}
	
}
