package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.admin.domain.Message;
import com.qhx.admin.mapper.MessageMapper;
import com.qhx.admin.model.to.message.MessDeleteTo;
import com.qhx.admin.model.to.message.MessQueryTo;
import com.qhx.admin.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.common.util.StringUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-11
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public List<Message> getAllMes(MessQueryTo messQueryTo)
    {
        String content = messQueryTo.getContent();
        LocalDateTime beginTime = messQueryTo.getBeginTime();
        LocalDateTime endTime = messQueryTo.getEndTime();

        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<Message>()
                .like(StringUtil.isNotEmpty(content), Message::getContent, content)
                .ge(StringUtil.isNotEmpty(beginTime), Message::getSenderTime, beginTime)
                .le(StringUtil.isNotEmpty(endTime), Message::getSenderTime, endTime);

        return this.list(queryWrapper);
    }

    @Override
    public boolean deleteMes(MessDeleteTo messDeleteTo)
    {
        List<Long> mesIds = messDeleteTo.getMesIds(); // 变相的减少连接
        if(StringUtil.isEmpty(mesIds) || mesIds.size() == 0){
            return true;
        }
        LambdaQueryWrapper<Message> deleteWrapper = new LambdaQueryWrapper<Message>()
                .in(Message::getMessageId, messDeleteTo.getMesIds());

        return this.remove(deleteWrapper);
    }

    @Override
    public boolean updateStatus(Message message)
    {
        LambdaQueryWrapper<Message> updateWrapper = new LambdaQueryWrapper<Message>()
                .eq(Message::getMessageId, message.getMessageId());

        Message newMes = new Message();
        newMes.setStatus( message.getStatus());
        return this.update(newMes,updateWrapper);
    }
}
