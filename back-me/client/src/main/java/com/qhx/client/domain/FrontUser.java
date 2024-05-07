package com.qhx.client.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qhx.common.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@Getter
@Setter
@TableName("ap_front_user")
@ApiModel(value = "FrontUser对象", description = "用户信息表")
public class FrontUser extends User{

    @ApiModelProperty("邀请码")
    private String invitationCode;
}
