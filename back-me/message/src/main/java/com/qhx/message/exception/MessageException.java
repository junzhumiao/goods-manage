package com.qhx.message.exception;

import com.qhx.common.exception.base.BaseException;

/**
 * @author: jzm
 * @date: 2024-03-12 11:42
 **/

public class MessageException extends BaseException
{
    public MessageException(String message, int code)
    {
        super(message, code);
    }

    public MessageException(String message)
    {
        super(message);
    }
}
