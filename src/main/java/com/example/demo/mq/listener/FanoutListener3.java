package com.example.demo.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.MQConfig;

@RabbitListener(queues = MQConfig.EXCHANGE_QUEUE5_NAME)
@Component
public class FanoutListener3 {
	
	@RabbitHandler
    public void process(String message) {
        System.out.println("topicReceiver5  : " + message);
    }

}
