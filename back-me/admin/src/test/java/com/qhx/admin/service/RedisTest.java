package com.qhx.admin.service;

import cn.hutool.core.date.DateUtil;
import com.qhx.common.util.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-03-04 17:58
 **/

@SpringBootTest
public class RedisTest
{
    @Autowired
    private RedisCache redisCache;


    @Test
    public void test1(){
        // 返回这个是s
        long expire = redisCache.getExpire("people:2");

        System.out.println(12);
    }
}
