package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qhx.common.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author qhx2004
 * @since 2024-02-27
 */
@Getter
@Setter
@TableName("ap_back_user")
@ApiModel(value = "BackUser对象", description = "用户信息表")
public class BackUser extends User
{
}
