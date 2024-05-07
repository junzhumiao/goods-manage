package com.qhx.admin.service;

import com.qhx.admin.model.to.LoginTo;
import com.qhx.common.model.AjaxResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-02-28 17:21
 **/

@SpringBootTest
public class LoginServiceTest
{

    @Autowired
    private BackUserService loginService;

    @Test
    public void login(){
        LoginTo loginTo = new LoginTo();
        loginTo.setPassword("123456");

    }
}
