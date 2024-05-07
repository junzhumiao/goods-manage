package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("ap_shop")
@ApiModel(value = "Shop对象", description = "")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("后台用户id(绑定的是后台农户)")
    private Long userId;

    @ApiModelProperty("商家名称(指店铺名称,不是用户名称)")
    private String shopName;

    @ApiModelProperty("评分人数")
    private Long scoreNum;

    @ApiModelProperty("购买人数")
    private Long buyNum;

    @ApiModelProperty("收藏人数")
    private Long collectNum;

    @ApiModelProperty("总评分")
    private Short totalScore;

    @ApiModelProperty("商家头像")
    private String shopAvatar;


    @ApiModelProperty("开通时间")
    private String createTime;

    @ApiModelProperty("修改时间")
    private String updateTime;

}
