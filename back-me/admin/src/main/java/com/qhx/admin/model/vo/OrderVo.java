package com.qhx.admin.model.vo;

import com.qhx.admin.domain.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单响应To
 *
 * @author: jzm
 * @date: 2024-03-10 17:13
 **/

@Data
public class OrderVo extends Order
{

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺头像")
    private String shopAvatar;

    @ApiModelProperty("商品名称")
    private String goodsName;

}
