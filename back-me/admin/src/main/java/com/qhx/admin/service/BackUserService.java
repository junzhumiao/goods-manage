package com.qhx.admin.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.Menu;
import com.qhx.admin.model.to.LoginTo;
import com.qhx.admin.model.to.backUser.BackUserDeleteTo;
import com.qhx.admin.model.to.backUser.BackUserQueryTo;
import com.qhx.common.model.to.user.PassTo;
import com.qhx.common.model.to.user.PasswordNotOldTo;

import java.util.List;

/**
 * 农户业务类
 * @author: jzm
 * @date: 2024-03-01 19:47
 **/

public interface BackUserService extends IService<BackUser>
{

    BackUser login(LoginTo loginTo);
    boolean isPasswordExists(String password);
    boolean isMD5PassExists(String password);

    // 不存在返回true
    boolean checkPasswordUnique(String password);

    boolean checkPhoneUnique(BackUser backUser);
    // 忽略当前电话和查询用户电话是否相等。
    boolean checkUpPhoneUnique(BackUser backUser);
    boolean checkEmailUnique(BackUser backUser);
    boolean checkUpEmailUnique(BackUser backUser);



    // 拿到用户拥有的权限路径
    List<Menu> getMenuByUserId(Long userId);

    List<BackUser> getAllBackUser(BackUserQueryTo backUserQueryTo, int roleId);

    BackUser getBackUserByUserId(long userId);

    boolean createBackUser(BackUser backUser);

    // 我想想,我对于表的限制策略: ,要求:密码和password是必填项且唯一。其他可以为null。电话号可以唯一。
    boolean updateBackUser(BackUser backUser);

    boolean updatePassword(PassTo passTo);
    boolean updateMD5Pass(PasswordNotOldTo passwordNotOldTo);

    boolean deleteBackUser(BackUserDeleteTo backUserDeleteTo);

    boolean updateStatus(BackUser backUser);


    BackUser getBackUserByField(SFunction<BackUser,?> colum,Object val);

    boolean updateAvatar(BackUser backUser);

}
