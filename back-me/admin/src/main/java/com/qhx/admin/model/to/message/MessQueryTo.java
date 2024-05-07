package com.qhx.admin.model.to.message;

import com.qhx.common.model.to.BasePageTo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author: jzm
 * @date: 2024-03-11 08:19
 **/

@Data
@EqualsAndHashCode(callSuper=false)
public class MessQueryTo extends BasePageTo
{
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String content;
}
