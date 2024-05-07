package com.qhx.admin.controller.system;

import com.qhx.admin.service.CaptchaService;
import com.qhx.common.model.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 *
 * @author: jzm
 * @date: 2024-03-01 19:52
 **/
@RestController
public class CaptchaController
{
    @Autowired
    private CaptchaService captchaService;

    @RequestMapping (path = "/get/captcha",method = RequestMethod.GET)
    @ApiOperation("获取验证码")
    public AjaxResult getCaptcha(){
        return captchaService.getCaptcha();
    }

}
