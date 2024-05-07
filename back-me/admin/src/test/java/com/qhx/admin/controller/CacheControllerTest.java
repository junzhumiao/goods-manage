package com.qhx.admin.controller;

import com.qhx.admin.controller.monitor.CacheController;
import com.qhx.common.model.AjaxResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-03-29 19:06
 **/

@SpringBootTest
public class CacheControllerTest
{
    @Autowired
    private CacheController cacheController;

    @Test
    public void test() throws Exception
    {
        AjaxResult info = cacheController.getInfo();
        System.out.println(12);
    }
}
