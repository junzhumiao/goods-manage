package com.qhx.common.model.to.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-07 14:42
 **/

@Setter
@Getter
public class UserDeleteTo
{
    private List<Long> userIds;
}
