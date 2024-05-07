package com.qhx.client.service;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.client.domain.BackUser;
import com.qhx.client.mapper.BackUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-17 17:18
 **/

@SpringBootTest
public class BackUserServiceTest
{

    @Autowired
    private BackUserMapper backUserMapper;

    @Test
    public void getOneTest(){
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<BackUser>()
                //.eq(BackUser::getPassword, "e10adc3949ba59abbe56e057f20f883e")
                .eq(BackUser::getUsername, "admin");
        // 拿不出数据的原因
        BackUser backUser = backUserMapper.selectOne(queryWrapper);
        List<BackUser> backUsers = backUserMapper.selectList(null);
        System.out.println(backUsers.toString());
        System.out.println(backUser);
    }

    @Test
    public void test(){
        System.out.println(MD5.create().digestHex("qhx2009"));
    }



}
