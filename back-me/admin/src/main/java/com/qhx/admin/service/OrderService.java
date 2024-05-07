package com.qhx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.Order;
import com.qhx.admin.model.to.order.OrderDeleteTo;
import com.qhx.admin.model.to.order.OrderQueryTo;
import com.qhx.admin.model.vo.OrderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
public interface OrderService extends IService<Order> {


    // 获取未完成订单
    List<Order> getNoDoneOrder(Long goodsId);

    List<Order> getAllOrder(OrderQueryTo orderQueryTo,Long shopId);

    OrderVo getOneByOrderId(Long orderId);

    boolean createOrder(Order order);

    boolean updateOrder(Order order);

    boolean updateStatus(Order order);

    boolean isCanDeleteOrder(OrderDeleteTo orderDeleteTo);
    boolean deleteOrder(OrderDeleteTo orderDeleteTo);

    boolean updateRefund(Order order);

    boolean updateCancel(Order order);
}
