package com.example.demo.mq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.config.MQConfig;
import com.example.demo.entity.DelayMission;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageDelayQueue {
	
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	public void send(DelayMission mission) {
		log.info("Send to delay queue: mission={}", mission);
		this.rabbitTemplate.convertAndSend(MQConfig.TTL, mission, new DelayMessagePostProcessor(mission.getDelayTime()));
	}

}
