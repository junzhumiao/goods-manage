package com.qhx.admin.model.to.message;

import lombok.Data;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-11 08:24
 **/

@Data
public class MessDeleteTo
{
    private List<Long> mesIds;
}
