package com.qhx.common.model.to.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 重置密码：不需要用户输入旧密码
 * @author: jzm
 * @date: 2024-03-07 19:13
 **/

@Setter
@Getter
public class PasswordNotOldTo
{
    String password; // MD5加密之后
    String newPassword; // 新密码
}
