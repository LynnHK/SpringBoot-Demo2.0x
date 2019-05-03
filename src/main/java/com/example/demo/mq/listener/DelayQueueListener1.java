package com.example.demo.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.MQConfig;
import com.example.demo.entity.DelayMission;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RabbitListener(queues = MQConfig.DELAY_QUEUE)
@Component
public class DelayQueueListener1 {
	
	@RabbitHandler
    public void process(DelayMission mission) {
       log.info("delay queue1 receive: {}", mission);
    }

}
