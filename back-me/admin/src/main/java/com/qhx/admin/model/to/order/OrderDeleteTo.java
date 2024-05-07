package com.qhx.admin.model.to.order;

import lombok.Data;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-10 14:13
 **/

@Data
public class OrderDeleteTo
{
    private List<Long> orderIds;
}
