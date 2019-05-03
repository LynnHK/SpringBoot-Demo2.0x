package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.LogInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public LogInterceptor getLogInterceptor() {
		return new LogInterceptor();
	}
	
	/**
	 * 拦截器注册
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getLogInterceptor()).addPathPatterns("/*/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
