package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	
	public static final String MQTEST_QUEUE_NAME = "mqtest";
	public static final String TOPIC_EXCHANGE_NAME = "exchange";
	public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";
	
	public static final String EXCHANGE_QUEUE_NAME = "topic.message";
	public static final String EXCHANGE_QUEUE2_NAME = "topic.messages";
	public static final String EXCHANGE_QUEUE3_NAME = "fanout.A";
	public static final String EXCHANGE_QUEUE4_NAME = "fanout.B";
	public static final String EXCHANGE_QUEUE5_NAME = "fanout.C";
	
	public static final String EXCHANGE_ROUTING_KEY = "topic.message";
	public static final String EXCHANGE_ROUTING2_KEY = "topic.#";
	
	/** Time-To-Live Extensions */
	public static final String TTL = "queue_ttl";
    public static final String DELAY_QUEUE = "queue_delay";
 
    public static final String TTL_EX = "exchange_ttl";
    /** Dead-Letter-Exchange */
    public static final String DLX = "exchange_dlx";
    
    public static final String TTL_KEY = "queue_ttl";
    public static final String DLX_KEY = "queue_delay";
	
	/**
	 * 点对点，即使有多个消费者，一条消息只有一个消费者获得
	 * 集群分布式等同步数据，不会获得重复数据进行重复处理
	 * 可在RabbitMQ后台页面自行创建
	 * 该bean实例在项目中并无作用
	 * @return
	 */
	@Bean
    public Queue testQueue() {
        return new Queue(MQTEST_QUEUE_NAME);
    }
	
	/**
	 * 广播模式，即发布订阅模式，只要有消息，消费者全部都可以拿到同样的消息
	 * topic是RabbitMQ中最灵活的一种方式，可以根据binding_key自由的绑定不同的队列
	 * @return
	 */
	@Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }
	
	//===============以下是验证topic Exchange的队列==========
    @Bean
    public Queue queueMessage() {
        return new Queue(EXCHANGE_QUEUE_NAME);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(EXCHANGE_QUEUE2_NAME);
    }
    
    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage 队列bean实例名，即上述配置方法名，如果只有一个实例，则忽略名称进行匹配
     * @param exchange 交换器bean实例名，即上述配置方法名，如果只有一个实例，则忽略名称进行匹配
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(EXCHANGE_ROUTING_KEY);
    }
    
    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with(EXCHANGE_ROUTING2_KEY);
    }
	//===============以上是验证topic Exchange的队列==========
    
    /**
     * 即topic无需绑定binding_key
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }
    
	//===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue AMessage() {
        return new Queue(EXCHANGE_QUEUE3_NAME);
    }

    @Bean
    public Queue BMessage() {
        return new Queue(EXCHANGE_QUEUE4_NAME);
    }

    @Bean
    public Queue CMessage() {
        return new Queue(EXCHANGE_QUEUE5_NAME);
    }
    
	@Bean
    public Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }
    
    @Bean
    public Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }
    
    @Bean
    public Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
    //===============以上是验证Fanout Exchange的队列==========
    
	//===============以下是验证延迟队列的配置==========
    /**
     * 缓冲队列
     * @return
     */
    @Bean
    public Queue ttlQueue() {
    	return QueueBuilder.durable(TTL)
    			.withArgument("x-dead-letter-exchange", DLX)
    			.withArgument("x-dead-letter-routing-key", DLX_KEY)
    			.build();
    }
    
    /**
     * 对外的可监听队列通道
     * @return
     */
    @Bean
    public Queue delayQueue() {
    	return new Queue(DELAY_QUEUE, true); // 默认为true，可以省略
    }
    
    /**
     * 缓冲交换机
     * @return
     */
    @Bean
    public DirectExchange ttlExchange() {
    	return new DirectExchange(TTL_EX);
    }
    
    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange dlxExchange() {
    	return new DirectExchange(DLX);
    }
    
    @Bean
    public Binding bindingExchangeTTL(Queue ttlQueue, DirectExchange ttlExchange) {
        return BindingBuilder.bind(ttlQueue).to(ttlExchange).with(TTL_KEY);
    }
    
    @Bean
    public Binding bindingExchangeDelay(Queue delayQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(delayQueue).to(dlxExchange).with(DLX_KEY);
    }
	//===============以上是验证延迟队列的配置==========
    
}
