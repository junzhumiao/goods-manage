package com.qhx.admin;

/**
 * @author: jzm
 * @date: 2024-02-27 19:48
 **/


import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.admin.config.mybatisplus.MybatisPlusConfig;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.GoodsImg;
import com.qhx.admin.mapper.BackUserMapper;
import com.qhx.admin.service.GoodsImgService;
import com.qhx.common.constant.Constant;
import com.qhx.common.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author: jzm
 * @date: 2024-02-27 19:43
 **/
@SpringBootTest()
public class AdminApplicationTest
{

    @Autowired
    private BackUserMapper backUserMapper;

    @Autowired
    private DataSource dynamicDataSource;


    @Autowired
    private MybatisPlusConfig metaObjectHandler;


    @Autowired
    private GoodsImgService goodsImgService;


    @Test
    public void GetAllUserTest() throws SQLException
    {
        BackUser user = new BackUser();
        user.setUsername("a");
        HashMap<String, Object> map = new HashMap<>();
        map.put("q",user);
        map.put("roleId", Constant.Role_Admin);

        List<BackUser> users = backUserMapper.selectAllUser(map);

    }

    @Test
    public void updateUser() throws SQLException
    {
        System.out.println(MD5.create().digestHex("123456"));
        BackUser user = new BackUser();
        user.setStatus("1");
        user.setUserId(2L);

        LambdaQueryWrapper<BackUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BackUser::getUserId,user.getUserId());
        //
        //
        //
        //backUserMapper.update(user,wrapper);
        //System.out.println(23);
        //UpdateWrapper<BackUser> wrapper
        //        = new UpdateWrapper<>();
        //// 这个算是直接拼接sql语句吧
        //wrapper.eq("user_id",user.getUserId());
        //wrapper.set("status",user.getStatus());
        int update = backUserMapper.update(user, wrapper);
        System.out.println(update);
    }

    @Test
    // 测试我们用一个字段值去找到并把它修改
    public void updateUserTwo() throws SQLException
    {
        BackUser user = new BackUser();
        user.setUsername("qhx2003");

        LambdaQueryWrapper<BackUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BackUser::getUsername,"qhx2004");
        backUserMapper.update(user,wrapper);
    }







    /**
     * @return 2034/10/25/
     */
    public String getDateDirPath(){
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtil.getYear());
        sb.append("/");
        sb.append(DateUtil.getMonth());
        sb.append("/");
        sb.append(DateUtil.getDay());
        sb.append("/");
        return  sb.toString();
    }

    @Test
    public void DateTest() throws SQLException,  IOException
    {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK);

    }

    @Test
    public void mainTest() throws SQLException,  IOException
    {
        List<GoodsImg> list = goodsImgService.list();
        System.out.println(12);
    }








}
