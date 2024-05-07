package com.qhx.common.exception;

import com.qhx.common.exception.base.BaseException;

/**
 * 处理业务之外的异常
 * @author: jzm
 * @date: 2024-03-10 15:27
 **/

public class GlobalException extends BaseException
{
    public GlobalException(String message, int code)
    {
        super(message, code);
    }

    public GlobalException(String message)
    {
        super(message);
    }
}
