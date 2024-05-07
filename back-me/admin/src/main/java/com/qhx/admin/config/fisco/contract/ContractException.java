package com.qhx.admin.config.fisco.contract;


import com.qhx.common.exception.base.BaseException;

/**
 * @author: jzm
 * @date: 2024-04-16 19:30
 **/

public class ContractException extends BaseException
{
    public ContractException(String message, int code)
    {
        super(message, code);
    }

    public ContractException(String message)
    {
        super(message, 400);
    }
}
