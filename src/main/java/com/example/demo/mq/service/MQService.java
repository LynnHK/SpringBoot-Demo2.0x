package com.example.demo.mq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.MQConfig;

@Service
public class MQService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String message) {
		this.rabbitTemplate.convertAndSend(MQConfig.MQTEST_QUEUE_NAME, message);
	}
	
	public void topic(String message) {
		this.rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, 
				MQConfig.EXCHANGE_ROUTING_KEY, message);
	}
	
	public void topic2(String message) {
		this.rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME, 
				MQConfig.EXCHANGE_ROUTING2_KEY, message);
	}
	
	public void topic3(String message) {
		this.rabbitTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE_NAME, "unavailable rooting-key", message);
	}
	
}
