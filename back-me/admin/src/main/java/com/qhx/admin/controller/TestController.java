package com.qhx.admin.controller;

import cn.hutool.json.JSONUtil;
import com.qhx.common.model.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author: jzm
 * @date: 2024-02-28 18:46
 **/

@RestController
public class TestController
{


     // 请求接口,但是没有响应
   @GetMapping("/test")
    public String test(LocalDateTime startTime){
       System.out.println(startTime);
        return JSONUtil.toJsonStr(AjaxResult.success("test 测试成功"));
    }


    @GetMapping("/test1")
    public String test1(){
        return JSONUtil.toJsonStr(AjaxResult.success("test 测试成功"));
    }

}
