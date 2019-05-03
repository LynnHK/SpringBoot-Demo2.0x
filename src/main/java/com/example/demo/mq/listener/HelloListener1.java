package com.example.demo.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.MQConfig;

@RabbitListener(queues = MQConfig.MQTEST_QUEUE_NAME)
@Component
public class HelloListener1 {
	
	@RabbitHandler
    public void process(String message) {
        System.out.println("Receiver1  : " + message);
    }

}
