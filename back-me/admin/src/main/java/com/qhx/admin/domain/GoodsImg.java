package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-17
 */
@Getter
@Setter
@TableName("ap_goods_img")
@ApiModel(value = "GoodsImg对象", description = "") // 添加孩子欧
public class GoodsImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品图片id")
    @TableId(value = "img_id", type = IdType.AUTO)
    private Long imgId;

    @ApiModelProperty("图片网络地址")
    private String imgUrl;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
