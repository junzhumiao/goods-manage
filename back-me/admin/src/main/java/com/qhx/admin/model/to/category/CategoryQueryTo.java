package com.qhx.admin.model.to.category;

import com.qhx.common.model.to.BasePageTo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: jzm
 * @date: 2024-03-07 22:23
 **/

@Setter
@Getter
public class CategoryQueryTo extends BasePageTo
{
    private String categoryName;
    private String status;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
}
