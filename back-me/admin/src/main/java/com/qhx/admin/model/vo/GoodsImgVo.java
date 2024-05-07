package com.qhx.admin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jzm
 * @date: 2024-03-27 09:39
 **/

@Data
public class GoodsImgVo
{
    @ApiModelProperty("商品图片id")
    @TableId(value = "img_id", type = IdType.AUTO)
    private Long imgId;

    @ApiModelProperty("图片网络地址")
    private String imgUrl;
}
