package com.qhx.message.controller;

import com.qhx.common.model.AjaxResult;
import com.qhx.message.config.rabbitMq.domain.Message;
import com.qhx.message.config.rabbitMq.service.RabbitMqService;
import com.qhx.message.service.ChatSessionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author: jzm
 * @date: 2024-03-11 21:57
 **/


@RestController
@RequestMapping("/chat")
public class ChatController
{

    @Autowired
    private ChatServerEndpoint chatServerEndpoint;

    @Autowired
    private ChatSessionService chatSessionService;



    @RequestMapping(path = "/sendMes",method = RequestMethod.POST)
    public AjaxResult sendMes(@RequestBody Message message){
        chatServerEndpoint.sendMes(message);
        return AjaxResult.success(message);
    }

    @RequestMapping(path = "/info")
    @ApiOperation("根据用户id查找用户信息")
    public AjaxResult getUserInfo(Long userId){

        return AjaxResult.success();
    }


    @RequestMapping(path = "/self/{senderId}/{receiverId}")
    @ApiOperation("获取指定用户的聊天信息内容")
    public AjaxResult selfList(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId){
        List<Message> messages = chatSessionService.selfList(senderId, receiverId);
        return AjaxResult.success(messages);
    }



}
