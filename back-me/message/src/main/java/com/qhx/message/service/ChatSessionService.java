package com.qhx.message.service;

import com.qhx.common.domain.User;
import com.qhx.message.config.rabbitMq.domain.Message;

import java.util.List;

public interface ChatSessionService
{
    /**
     * 根据ID从Redis中查询数据
     *
     * @param userId
     * @return User对象
     */
    User findById(Long userId);

    /**
     * 推送消息，储存到Redis数据库中
     *
     * @param message 消息
     */
    void pushMessage(Message message);

    /**
     * 获取在线用户列表
     *
     * @return
     */
    List<User> onlineList();

    /**
     * 获取公共消息内容 -- 群组
     *
     * @return
     */
    List<Message> commonList();

    /**
     * 获取该用户与指定窗口的推送消息
     *
     * @param senderId 推送方ID
     * @param receiverId   接收方ID
     * @return
     */
    List<Message> selfList(Long senderId, Long receiverId);

    /**
     * 删除指定ID在Redis中储存的数据
     *
     * @param userId
     */
    void delete(Long userId);
}
