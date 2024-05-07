package com.qhx.admin.model.to.goods;

import lombok.Data;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-08 18:50
 **/

@Data
public class GoodsDeleteTo
{
    private List<Long> goodsIds;
}
