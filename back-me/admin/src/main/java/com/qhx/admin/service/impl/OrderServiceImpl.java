package com.qhx.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.Order;
import com.qhx.admin.mapper.OrderMapper;
import com.qhx.admin.model.to.order.OrderDeleteTo;
import com.qhx.admin.model.to.order.OrderQueryTo;
import com.qhx.admin.model.vo.OrderVo;
import com.qhx.admin.service.BackUserService;
import com.qhx.admin.service.FrontUserService;
import com.qhx.admin.service.OrderService;
import com.qhx.common.constant.Constant;
import com.qhx.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private BackUserService backUserService;

    @Autowired
    private FrontUserService frontUserService;


    @Override
    public List<Order> getNoDoneOrder(Long goodsId)
    {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .ne(Order::getStatus, Constant.Order_Done)
                .eq(Order::getGoodsId, goodsId);

        return this.list(queryWrapper);
    }

    @Override
    public List<Order> getAllOrder(OrderQueryTo orderQueryTo,Long shopId)
    {
        String receiverPhone = orderQueryTo.getReceiverPhone();
        String senderPhone = orderQueryTo.getSenderPhone();
        LocalDateTime beginTime = orderQueryTo.getBeginTime();
        LocalDateTime endTime = orderQueryTo.getEndTime();
        String orderNum = orderQueryTo.getOrderNum();

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(StringUtil.isNotEmpty(orderNum),Order::getOrderNum,orderNum)
                .eq(StringUtil.isNotEmpty(receiverPhone), Order::getReceiverPhone, receiverPhone)
                .eq(StringUtil.isNotEmpty(senderPhone), Order::getSenderPhone, senderPhone)
                .ge(StringUtil.isNotEmpty(endTime), Order::getCreateTime, beginTime)
                .le(StringUtil.isNotEmpty(beginTime), Order::getCreateTime, endTime)
                .eq(StringUtil.isNotEmpty(shopId),Order::getShopId,shopId);


        return this.list(wrapper);
    }

    @Override
    public OrderVo getOneByOrderId(Long orderId)
    {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, orderId);
        Order one = this.getOne(queryWrapper);
        if(StringUtil.isEmpty(one)){
            return null;
        }
        OrderVo orderResVo = new OrderVo();
        BeanUtil.copyProperties(one,orderResVo,false);
        return orderResVo;
    }




    @Override
    public boolean createOrder(Order order)
    {
        return this.save(order);
    }

    @Override
    public boolean updateOrder(Order order)
    {
        LambdaQueryWrapper<Order> updateWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, order.getOrderId());

        return this.update(order,updateWrapper);
    }



    @Override
    public boolean updateStatus(Order order)
    {

        LambdaQueryWrapper<Order> updateWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, order.getOrderId());

        Order newOrder = new Order();
        newOrder.setStatus(order.getStatus());

        return this.update(newOrder,updateWrapper);
    }

    @Override
    public boolean isCanDeleteOrder(OrderDeleteTo orderDeleteTo)
    {
        List<Long> orderIds = orderDeleteTo.getOrderIds();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
                .in(Order::getOrderId, orderIds);

        List<Order> orders = this.list(queryWrapper);
        for (Order order : orders) // 判断订单是否有未完成 || 未取消
        {
            if(!StringUtil.equals(order.getStatus(), Constant.Order_Done) || StringUtil.equals(order.getCancel(),Constant.Enable)){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean deleteOrder(OrderDeleteTo orderDeleteTo)
    {
        List<Long> orderIds = orderDeleteTo.getOrderIds();
        LambdaQueryWrapper<Order> deleteWrapper = new LambdaQueryWrapper<Order>()
                .in(!Objects.isNull(orderIds), Order::getOrderId, orderIds);

        return this.remove(deleteWrapper);
    }

    @Override
    public boolean updateRefund(Order order)
    {
        Order newOrder = new Order();
        newOrder.setRefund(order.getRefund());

        LambdaQueryWrapper<Order> updateWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, order.getOrderId());

        return  this.update(newOrder, updateWrapper);
    }

    @Override
    public boolean updateCancel(Order order)
    {
        Order newOrder = new Order();
        newOrder.setCancel(order.getCancel());

        LambdaQueryWrapper<Order> updateWrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, order.getOrderId());
        return this.update(newOrder, updateWrapper);
    }


}