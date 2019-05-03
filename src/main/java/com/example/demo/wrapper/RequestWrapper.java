package com.example.demo.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 配合filter的使用，对HttpServletRequest进行再次包装，以便多次读取流数据
 * @author lee
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 存储body数据的容器
	 */
	private final byte[] body;
	
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		
		// 将body数据存储起来
		String bodyStr = getBodyAsString(request);
		body = bodyStr.getBytes(Charset.defaultCharset());
	}
	
	/**
	 * 获取请求Body
	 * @param request request
	 * @return String
	 */
	private String getBodyAsString(final ServletRequest request) {
		try {
			return inputStream2String(request.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将inputStream里的数据读取出来并转换成字符串
	 * @param inputStream inputStream
	 * @return String
	 */
	public static String inputStream2String(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
			@Override
			public boolean isFinished() {
				return false;
			}
			@Override
			public boolean isReady() {
				return false;
			}
			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
		
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
	
	/**
	 * 获取请求Body
	 * @return String
	 */
	public String getBodyString() {
		final InputStream inputStream = new ByteArrayInputStream(body);
		return inputStream2String(inputStream);
	}

}
