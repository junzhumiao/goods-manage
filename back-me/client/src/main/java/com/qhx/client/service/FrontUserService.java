package com.qhx.client.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.client.domain.BackUser;
import com.qhx.client.domain.FrontUser;
import com.qhx.client.model.to.LoginTo;
import com.qhx.client.model.to.RegisterTo;

public interface FrontUserService extends IService<FrontUser>
{
    FrontUser login(LoginTo loginTo);

    boolean checkPasswordUnique(String password);

    boolean checkPhoneUnique(String phone);

    boolean checkEmailUnique(String email);

    FrontUser getFrontUserByField(SFunction<FrontUser, ?> colum, Object val);

    boolean createFrontUser(RegisterTo registerTo);

    boolean createFrontUser(FrontUser frontUser);

    BackUser isBindBackUser(String md5Pass);


}
