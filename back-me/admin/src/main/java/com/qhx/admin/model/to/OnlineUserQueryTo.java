package com.qhx.admin.model.to;

import com.qhx.common.model.to.BasePageTo;
import lombok.Data;

/**
 * @author: jzm
 * @date: 2024-03-28 20:41
 **/

@Data
public class OnlineUserQueryTo extends BasePageTo
{
    private String loginIp;
    private String username;
}
