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
 *
 * @author qhx2004
 * @since 2024-03-19
 */
@Getter
@Setter
@TableName("ap_order")
@ApiModel(value = "Order对象", description = "")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @NotEmpty(extra = "订单编号")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @ApiModelProperty("购买者用户id")
    @NotEmpty(extra = "购买者用户编号")
    private Long buyerId;

    @ApiModelProperty("商家id(后台农户id)")
    @NotEmpty(extra = "商家编号")
    private Long shopId;

    @ApiModelProperty("农产品id")
    @NotEmpty(extra = "农产品编号")
    private Long goodsId;

    @ApiModelProperty("订单交易数量")
    @NotEmpty(extra = "订单交易数量")
    private Integer amount;

    @ApiModelProperty("订单总价")
    @NotEmpty(extra = "订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单状态(0 待支付,1 未发货 , 2 已发货 , 3 已完成)")
    private String status;

    @ApiModelProperty("收货人姓名")
    @NotEmpty(extra = "收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    @NotEmpty(extra = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货地址")
    @NotEmpty(extra = "收货人地址")
    private String receiverAddress;

    @ApiModelProperty("快递名称")
    private String expressName;

    @ApiModelProperty("快递单号")
    private String expressNum;

    @ApiModelProperty("发货人地址")
    private String senderAddress;

    @ApiModelProperty("发货人姓名")
    private String senderName;

    @ApiModelProperty("发货人电话")
    private String senderPhone;

    @ApiModelProperty("是否退款('0' 代表未退款,'1'代表退款)")
    private String refund;

    @ApiModelProperty("是否取消订单('0' 代表取消订单,'1'代表为取消订单)")
    private String cancel;

    @ApiModelProperty("订单序列号")
    private String orderNum;



}
