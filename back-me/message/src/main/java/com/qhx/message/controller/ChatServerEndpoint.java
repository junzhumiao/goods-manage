package com.qhx.message.controller;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qhx.common.domain.User;
import com.qhx.common.exception.PermissionException;
import com.qhx.message.config.rabbitMq.domain.Message;
import com.qhx.message.config.rabbitMq.service.RabbitMqService;
import com.qhx.message.service.ChatSessionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@Data
@ServerEndpoint("/chat/{userId}")
// websocket由于默认多对象导入,因此对于依赖注入的要求,也是多对象(但是由于默认是单例的,因此注入为看，)
public class ChatServerEndpoint
{
    private static  RabbitMqService rabbitMqService;
    private static ChatSessionService chatSessionService;

    {
        rabbitMqService  = SpringUtil.getBean(RabbitMqService.class);
        chatSessionService = SpringUtil.getBean(ChatSessionService.class);
    }



    private Session session;

    private Long currentUserId;


    private static CopyOnWriteArraySet<ChatServerEndpoint> chatEndpointSet = new CopyOnWriteArraySet<>();
    public static int olineCount;


    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.currentUserId = userId;
        // TODO 校验聊天id,查询登录用户是否存在
        User loginUser = chatSessionService.findById(userId);
        if(loginUser == null){
            throw new PermissionException("用户并未登录！");
        }
        chatEndpointSet.add(this);
        rabbitMqService.createLister(userId); // TODO 创建制定id的消息监听器
        addOnlineCount(); // 用户在线人数+1
        log.info("【websocket】有"+ "userId:"+userId+"来连接, 总数:{}", chatEndpointSet.size());
    }


    @OnClose
    public void onClose() {
        chatEndpointSet.remove(this);
        removeOnlineCount();
        log.info("【websocket】连接断开, 总数:{}", chatEndpointSet.size());
    }


    @OnMessage
    public void onMessage(String message) {
        log.info("接收到客户端：" + currentUserId + " 的信息：" + message);
        // TODO 这里暂且不用
    }

    @OnError
    //我记得上面onMessage()出异常,下面会捕获到。
    public void onError(Throwable e) {
      e.printStackTrace();
    }


    /**
     *  向客户端主动推送消息 ,如果客户端离线,先用消息队列缓存消息
     *
     * @param message 消息实体
     */
    public void sendMes(Message message) {
        boolean end = exSendMes(message);
        if(!end){
            rabbitMqService.sendMes(message);
        }
    }

    /**
     * 向客户端主动推送消息
     *
     * @param message 消息实体json类型
     * @return
     */
    public static boolean exSendMes(JSONObject message){
        return exSendMes(JSONUtil.toBean(message,Message.class));
    }

    // 接受Message发送到指定id用户
    public  static  boolean exSendMes(Message message){
        for (ChatServerEndpoint chatEndpoint : chatEndpointSet)
        {
            if(Objects.equals(message.getReceiverId(), chatEndpoint.currentUserId)){
                try
                {
                    sendText(chatEndpoint,message);
                    return true;
                } catch (Exception e)
                {
                   log.error("发送消息到userId:"+message.getReceiverId()+",出现异常:"+e.getMessage());
                   return false;
                }
            }
        }
        return false;
    }
    
    private static void sendText(ChatServerEndpoint chatServerEndpoint,Message message) throws IOException
    {
        chatServerEndpoint.getSession().getBasicRemote().sendText(JSONUtil.toJsonStr(message));
    }

    public static int getOnlineCount(){
        return olineCount;
    }

    public void addOnlineCount(){
        ChatServerEndpoint.olineCount++;
    }

    public void removeOnlineCount(){
        if(ChatServerEndpoint.olineCount < 0){ // 防止并发
            ChatServerEndpoint.olineCount = 0;
        }
        ChatServerEndpoint.olineCount--;
    }


}