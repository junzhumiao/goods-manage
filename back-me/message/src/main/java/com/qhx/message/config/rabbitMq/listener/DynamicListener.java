package com.qhx.message.config.rabbitMq.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qhx.message.controller.ChatServerEndpoint;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

/**
 * 自定义监听器处理
 */
@Slf4j
@Service
public class DynamicListener implements ChannelAwareMessageListener
{

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        String mes = new String(body);
        try
        {
            JSONObject userMesJson = JSONUtil.parseObj(mes);
            if (userMesJson.containsKey("receiverId")) // 接受者消息
            {
                ChatServerEndpoint.exSendMes(userMesJson);
            }
        }catch (Exception e){
            log.error("json 反序列化异常:"+e.getMessage());
            return;
        }

    }
}
