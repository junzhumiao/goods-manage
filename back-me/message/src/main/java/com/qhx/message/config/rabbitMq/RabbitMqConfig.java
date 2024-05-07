package com.qhx.message.config.rabbitMq;

import com.qhx.message.config.rabbitMq.listener.DynamicListener;
import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class RabbitMqConfig
{

	@Autowired
	private AmqpAdmin rabbitAdmin;

	@Autowired
	private DynamicListener dynamicListener;


	/** 默认交换机类型：DIRECT
	 *
	 * @param queueName
	 * @param exchange
	 * @param routingKey
	 */
	public void createExchangeBindQueue(String queueName,String exchange,String routingKey){
		createExchangeBindQueue(queueName,exchange,routingKey,BuiltinExchangeType.DIRECT.getType());
	}


	public void createExchangeBindQueue(String queueName,String exchange,String routingKey,String type){
		// 创建队列 - 交换机
		Properties properties = this.rabbitAdmin.getQueueProperties(queueName);
		if(properties==null) {
			Queue queue = new Queue(queueName, true, false, false, null);
			if(BuiltinExchangeType.DIRECT.getType().equals(type)) {
				DirectExchange directExchange = new DirectExchange(exchange); // 一般创建队列,交换机、
				this.rabbitAdmin.declareQueue(queue);
				this.rabbitAdmin.declareExchange(directExchange);
				this.rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
			}else if(BuiltinExchangeType.FANOUT.getType().equals(type)){
				FanoutExchange fanoutExchange = new FanoutExchange(exchange);
				this.rabbitAdmin.declareQueue(queue);
				this.rabbitAdmin.declareExchange(fanoutExchange);
				this.rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
			}else{
				TopicExchange topicExchange = new TopicExchange(exchange);
				this.rabbitAdmin.declareQueue(queue);
				this.rabbitAdmin.declareExchange(topicExchange);
				this.rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
			}
		}
	}


	/**
	 * 简单的监听器容器
	 *
	 * @param cachingConnectionFactory
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer(CachingConnectionFactory cachingConnectionFactory)
	{
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setConcurrentConsumers(10);
		container.setMaxConcurrentConsumers(10);
		container.setMessageListener(dynamicListener);
		container.start();
		return container;
	}




}
