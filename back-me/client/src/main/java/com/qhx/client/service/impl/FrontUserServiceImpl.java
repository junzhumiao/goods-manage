package com.qhx.client.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.client.domain.BackUser;
import com.qhx.client.domain.FrontUser;
import com.qhx.client.mapper.BackUserMapper;
import com.qhx.client.mapper.FrontUserMapper;
import com.qhx.client.model.to.LoginTo;
import com.qhx.client.model.to.RegisterTo;
import com.qhx.client.service.FrontUserService;
import com.qhx.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台业务类: 鉴于比较抽象,没有完成抽出复用,因此除了基本的,后台和前台业务单独写的
 *
 * @author: jzm
 * @date: 2024-03-17 09:57
 **/

@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements FrontUserService
{
    @Autowired
    private BackUserMapper backUserMapper;

    @Override
    public FrontUser login(LoginTo loginTo)
    {
        String phone = loginTo.getPhone();
        String password = loginTo.getPassword();
        password = MD5.create().digestHex(password);

        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(FrontUser::getPassword,password)
                .eq(FrontUser::getPhone,phone);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean checkPasswordUnique(String password)
    {
        password = MD5.create().digestHex(password);
        FrontUser user = getFrontUserByField(FrontUser::getPassword, password);
        return StringUtil.isEmpty(user);
    }

    @Override
    public boolean checkPhoneUnique(String phone)
    {
        FrontUser user = getFrontUserByField(FrontUser::getPhone, phone);
        return  StringUtil.isEmpty(user);
    }

    @Override
    public boolean checkEmailUnique(String email)
    {
        FrontUser user = getFrontUserByField(FrontUser::getEmail, email);
        return StringUtil.isEmpty(user);
    }

    @Override
    public FrontUser getFrontUserByField(SFunction<FrontUser, ?> colum, Object val)
    {
        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(colum,val);
        FrontUser user = this.getOne(queryWrapper);
        return user;
    }

    @Override
    public boolean createFrontUser(RegisterTo registerTo)
    {
        FrontUser frontUser = new FrontUser();
        BeanUtil.copyProperties(registerTo,frontUser);
        return createFrontUser(frontUser);
    }

    @Override
    public boolean createFrontUser(FrontUser frontUser)
    {
        String username = frontUser.getUsername();
        if(StringUtil.isEmpty(username)){
            frontUser.setUsername("用户:"+frontUser.getPhone());
        }

        return this.save(frontUser);
    }

    @Override
    public BackUser isBindBackUser(String md5Pass)
    {
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<BackUser>()
                .eq(BackUser::getPassword, md5Pass);

        return backUserMapper.selectOne(queryWrapper);
    }
}
