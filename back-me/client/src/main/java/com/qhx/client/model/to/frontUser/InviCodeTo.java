package com.qhx.client.model.to.frontUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邀请码to
 *
 * @author: jzm
 * @date: 2024-03-10 22:03
 **/

@Data
public class InviCodeTo
{
    @ApiModelProperty("邀请码")
    private String invitationCode;
}
