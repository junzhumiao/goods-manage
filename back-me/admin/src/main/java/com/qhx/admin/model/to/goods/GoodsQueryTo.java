package com.qhx.admin.model.to.goods;

import com.qhx.common.model.to.BasePageTo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author: jzm
 * @date: 2024-03-08 17:53
 **/

@Setter
@Getter
public class GoodsQueryTo extends BasePageTo
{
    private String goodsName;
    private String goodsNum; // 产品唯一号
    private Long shopId;
    private String status;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
