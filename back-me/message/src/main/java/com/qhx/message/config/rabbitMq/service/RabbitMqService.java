package com.qhx.message.config.rabbitMq.service;

import cn.hutool.json.JSONUtil;
import com.qhx.message.config.rabbitMq.RabbitMqConfig;
import com.qhx.message.config.rabbitMq.domain.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息处理
 *
 * @author: jzm
 * @date: 2024-03-11 22:17
 **/

@Service
@Lazy // 延迟加载,当需要用这个bean时,才会自动加载
public class RabbitMqService
{

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitMqConfig rabbitMqConfig;

    @Resource
    private SimpleMessageListenerContainer simpleMessageListenerContainer;

    public void sendMes(Message message){
        // 创建消息队列,并将消息发送出去
        String exchange = "receiverId";
        String queueName = "receiverId:"+message.getReceiverId();
        String routingKey = "receiverId:" +message.getReceiverId();
        rabbitMqConfig.createExchangeBindQueue(queueName,exchange,routingKey);

        rabbitTemplate.convertAndSend(exchange,routingKey, JSONUtil.toJsonStr(message));
    }

    public void createLister(Long userId){
        simpleMessageListenerContainer.addQueueNames("receiverId:"+userId);
        simpleMessageListenerContainer.start();
    }



}
