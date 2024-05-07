package com.qhx.admin.service;

import com.qhx.admin.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-03-29 21:03
 **/
@SpringBootTest
public class OrderServiceTest
{
    @Autowired
    private OrderService orderService;

    @Test
    public void  updateOrderStatus(){
        Order order = new Order();
        order.setCancel("1");
        order.setOrderId(2L);
        boolean updateOrder = orderService.updateCancel(order);
        System.out.println(12);
    }
}
