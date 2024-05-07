package com.qhx.admin.model.to.order;

import com.qhx.common.model.to.BasePageTo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: jzm
 * @date: 2024-03-10 11:10
 **/

@Data
public class OrderQueryTo extends BasePageTo
{
    private String receiverPhone; // 联系电话
    private String senderPhone; // 发货人电话
    private String orderNum; // 订单序列号
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
