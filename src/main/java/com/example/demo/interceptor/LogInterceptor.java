package com.example.demo.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.example.demo.utils.DemoUtils;
import com.example.demo.utils.JSONUtil;
import com.example.demo.wrapper.RequestWrapper;
import com.example.demo.wrapper.ResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
	
	private static final String START_TIME = "startTime";
	private static final String JSON_CONTENT_TYPE = "application/json";

	@Value("${logback.logs.traceid}")
	private String traceId;
	
	/**
	 * 整个请求完成之后，通常用于资源清理
	 */
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(req, res, handler, ex);
		
		if (res instanceof ResponseWrapper) {
			ResponseWrapper response = (ResponseWrapper) res;
			String body = response.getBodyAsString(response.getCharacterEncoding());
			String contentType = response.getContentType();
			if (contentType == null || contentType.contains(JSON_CONTENT_TYPE)) {
				log.info("output results: {}", body == null ? "null" : body);
			}
		}
		
		// 记录请求响应时间
		long startTime = Long.parseLong(req.getAttribute(START_TIME).toString());
		long endTime = System.currentTimeMillis();
		log.info("{} for '{}' end, cost {}ms", req.getMethod(), req.getRequestURI(), (endTime - startTime));
		
		// 将完整日志记录数据库(生产环境)，此处省略
	}

	/**
	 * Controller完成之后，视图渲染之前
	 */
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(req, res, handler, modelAndView);
	}

	/**
	 * 进入Controller之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		// 添加日志唯一识别号
		MDC.put(traceId, DemoUtils.getUniqueMd5().substring(16).toUpperCase());
		
		// 记录开始时间
		log.info("{} for '{}' start... ip:{}", req.getMethod(), req.getRequestURI(), getIpAddress(req));
		req.setAttribute(START_TIME, System.currentTimeMillis());
		
		// 记录请求参数
		Map<String, Object> map = getParameterMap(req);
		log.info("input params: {}", map == null ? "null" : JSON.toJSONString(map));
		
		return HandlerInterceptor.super.preHandle(req, res, handler);
	}

	/**
	 * 获取请求参数
	 * @param req
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> getParameterMap(HttpServletRequest req) throws IOException {
		Map<String, Object> map = null;
		// 获取body参数
		if (JSON_CONTENT_TYPE.equals(req.getContentType())) {
			String body = RequestWrapper.inputStream2String(req.getInputStream());
			map = JSONUtil.parseMap(body);
		}
		
		// 获取url参数
		Map<String, String[]> getMap = req.getParameterMap();
		if (getMap != null && getMap.size() > 0) {
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			for (Map.Entry<String, String[]> entry : getMap.entrySet()) {
				String key = entry.getKey();
				String[] value = entry.getValue();
				map.put(key, value[0]);
			}
		}
		return map;
	}
	
	/**
	 * 获取请求的ip地址
	 * @param request
	 * @return
	 */
	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getRemoteAddr();
	    }
	    
	    return ip;  
	}
	
}
