package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {
	
	@Value("${maxPoolSize}")
	private int maxPoolSize;
	
	@Value("${corePoolSize}")
	private int corePoolSize;
	
	@Value("${queueCapacity}")
	private int queueCapacity;
	
	@Bean
	public TaskExecutor taskExecutor() {
		 ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		 taskExecutor.setMaxPoolSize(maxPoolSize);
		 taskExecutor.setCorePoolSize(corePoolSize);
		 taskExecutor.setQueueCapacity(queueCapacity);
		return taskExecutor;
	}

}
