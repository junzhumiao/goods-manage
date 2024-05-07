package com.qhx.client.model.to;

import lombok.Data;

/**
 * @author: jzm
 * @date: 2024-03-17 10:06
 **/

@Data
public class RegisterTo
{
    private String phone;
    private String password;
    private String username;
    private String invitationCode; // 邀请码
}
