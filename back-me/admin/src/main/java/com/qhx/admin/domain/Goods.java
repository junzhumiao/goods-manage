package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qhx.common.annotation.NotEmpty;
import com.qhx.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-19
 */
@Getter
@Setter
@TableName("ap_goods")
@ApiModel(value = "Goods对象", description = "")
public class Goods extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("农产品id")
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Long goodsId;

    @ApiModelProperty("农产品名称")
    @NotEmpty(extra = "农产品名称")
    private String goodsName;

    @ApiModelProperty("产品描述")
    private String description;

    @ApiModelProperty("价格")
    @NotEmpty(extra = "价格")
    private BigDecimal price;

    @ApiModelProperty("商品所属商家id。")
    private Long shopId;

    @ApiModelProperty("产品库存数量")
    @NotEmpty(extra = "产品库存数量")
    private Integer balance;


    @ApiModelProperty("已售产品数量")
    @NotEmpty(extra = "已售产品数量")
    private Integer sold;

    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    // 后端自定义生成的
    @ApiModelProperty("产品的唯一编码")
    private String goodsNum;
}
