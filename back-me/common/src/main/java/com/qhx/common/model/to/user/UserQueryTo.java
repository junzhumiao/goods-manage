package com.qhx.common.model.to.user;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qhx.common.model.to.BasePageTo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: jzm
 * @date: 2024-03-07 14:28
 **/

@Data
public class UserQueryTo extends BasePageTo
{
    private String username;
    private String phone;
    private String status;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
