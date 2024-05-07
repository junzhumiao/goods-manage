package com.qhx.common.exception.system;

import com.qhx.common.exception.base.BaseException;

/**
 * @author: jzm
 * @date: 2024-03-07 08:44
 **/

public class SystemException extends BaseException
{
    public SystemException(String message, int code)
    {
        super(message, code);
    }

    public SystemException(String message)
    {
        super(message);
    }
}
