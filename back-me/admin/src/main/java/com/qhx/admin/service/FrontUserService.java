package com.qhx.admin.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.FrontUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.common.model.to.user.PassTo;
import com.qhx.admin.model.to.frontUser.FrontUserDeleteTo;
import com.qhx.admin.model.to.frontUser.FrontUserQueryTo;
import com.qhx.common.model.to.user.PasswordNotOldTo;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
public interface FrontUserService  extends IService<FrontUser>
{

    FrontUser getOneByUserId(Long userId);

    List<FrontUser> getAllFrontUser(FrontUserQueryTo frontUserQueryTo);

   boolean createFrontUser(FrontUser frontUser);

    /**
     * 前台用户修改密码
     * @param passTo 
     * @return
     */
    boolean updatePassword(PassTo passTo);

    boolean updateStatus(FrontUser frontUser);

    boolean updateFrontUser(FrontUser frontUser);

    boolean deleteFrontUser(FrontUserDeleteTo frontUserDeleteTo);

    
    boolean checkPasswordUnique(String password);
    boolean checkMD5PassUnique(String password);
    boolean isPasswordExists(String password);
    
    boolean checkPhoneUnique(FrontUser backUser);
    boolean checkUpPhoneUnique(FrontUser frontUser);


    boolean checkEmailUnique(FrontUser backUser);
    boolean checkUpEmailUnique(FrontUser frontUser);


    boolean isMD5PassExists(String password);

    boolean updateMD5Pass(PasswordNotOldTo passwordNotOldTo);

    FrontUser getFrontUserByField(SFunction<FrontUser,?> colum,Object val);

    BackUser isBindBackUser(String md5Pass);


}
