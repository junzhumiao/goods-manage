package com.qhx.admin.service;

import com.qhx.admin.domain.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.model.to.message.MessDeleteTo;
import com.qhx.admin.model.to.message.MessQueryTo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-11
 */
public interface MessageService extends IService<Message> {

    List<Message> getAllMes(MessQueryTo messQueryTo);

    boolean deleteMes(MessDeleteTo messDeleteTo);

    boolean updateStatus(Message message);

}
