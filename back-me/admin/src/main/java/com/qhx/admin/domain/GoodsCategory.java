package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@Getter
@Setter
@TableName("ap_goods_category")
@ApiModel(value = "GoodsCategory对象", description = "")
@AllArgsConstructor
public class GoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品id")
    private Long goodsId;

    @ApiModelProperty("产品分类id")
    private Integer categoryId;
}
