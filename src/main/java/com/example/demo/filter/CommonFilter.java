package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.wrapper.RequestWrapper;
import com.example.demo.wrapper.ResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "commonFilter")
public class CommonFilter implements Filter {
	
	private static final String JSON_CONTENT_TYPE = "application/json";

	@Override
	public void destroy() {
		log.info("[commonFilter] destroy...");
		Filter.super.destroy();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// 对请求json格式数据进行包装，以便后续可以重复读取流
		String contentType = req.getContentType();
		if (contentType != null && contentType.contains(JSON_CONTENT_TYPE)) {
			req = new RequestWrapper(req);
		}
		ResponseWrapper responseWrapper = new ResponseWrapper(res);
		
		chain.doFilter(req, responseWrapper);
		
		// 重新封装response进行响应
		res.setContentType(responseWrapper.getContentType());
		for (String name : responseWrapper.getHeaderNames()) {
			res.addHeader(name, responseWrapper.getHeader(name));
		}
		res.getOutputStream().write(responseWrapper.getBodyAsByte());
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("[commonFilter] init...");
		Filter.super.init(filterConfig);
	}

}
