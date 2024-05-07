package com.qhx.common.handler;

import com.qhx.common.exception.base.BaseException;
import com.qhx.common.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器
 *
 * @author: jzm
 * @date: 2024-02-28 15:03
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler
{

    // 捕获基础异常
    @ExceptionHandler(BaseException.class)
    public AjaxResult handlerBaseException(BaseException ex){
        return AjaxResult.error(ex.getCode(),ex.getMessage());
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult handlerLimitExceededException(MaxUploadSizeExceededException ex){
        log.error("上传文件超过系统最大限制2MB!");
        return AjaxResult.error("上传文件超过系统最大限制2MB!");
    }

}
