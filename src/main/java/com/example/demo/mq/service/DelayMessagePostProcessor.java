package com.example.demo.mq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class DelayMessagePostProcessor implements MessagePostProcessor {
	
	/**
	 * 延迟时间（单位秒）
	 */
	private long ttl = 0L;
	 
	public DelayMessagePostProcessor(long ttl) {
		this.ttl = ttl;
	}

	@Override
	public Message postProcessMessage(Message message) throws AmqpException {
		message.getMessageProperties().setExpiration(Long.toString(ttl * 1000));
		return message;
	}

}
