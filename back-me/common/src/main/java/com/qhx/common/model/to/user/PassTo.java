package com.qhx.common.model.to.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改密码to
 * @author: jzm
 * @date: 2024-03-02 11:16
 **/

@Setter
@Getter
public class PassTo
{
    private String oldPassword;
    private String newPassword;
}
