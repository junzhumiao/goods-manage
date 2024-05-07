package com.qhx.common.model.to;

import lombok.Getter;
import lombok.Setter;

/**
 *  分页基本to
 *
 * @author: jzm
 * @date: 2024-03-03 21:04
 **/

@Setter
@Getter
public abstract class BasePageTo
{
    private int page;
    private int pageSize;
}
