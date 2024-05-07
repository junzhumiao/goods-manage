package com.qhx.message.service.impl;

import com.qhx.common.domain.User;
import com.qhx.common.util.redis.RedisCache;
import com.qhx.message.config.rabbitMq.domain.Message;
import com.qhx.message.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-12 12:11
 **/

@Service(value = "chatSessionService")
@Primary
public class ChatSessionServiceImpl implements ChatSessionService
{

    @Autowired
    private RedisCache redisCache;

    @Override
    public User findById(Long userId)
    {
        return null;
    }

    @Override
    public void pushMessage(Message message)
    {

    }

    @Override
    public List<User> onlineList()
    {
        return null;
    }

    @Override
    public List<Message> commonList()
    {
        return null;
    }

    @Override
    // 前台缓存的用户对话id，缓存的不是用户对话消息
    public List<Message> selfList(Long senderId, Long receiverId)
    {
        return null;
    }

    @Override
    public void delete(Long userId)
    {

    }
}
