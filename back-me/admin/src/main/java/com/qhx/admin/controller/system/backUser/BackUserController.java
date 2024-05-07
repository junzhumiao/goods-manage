package com.qhx.admin.controller.system.backUser;

import com.qhx.admin.domain.BackUser;
import com.qhx.admin.model.to.backUser.BackUserDeleteTo;
import com.qhx.admin.model.to.backUser.BackUserQueryTo;
import com.qhx.admin.service.BackUserService;
import com.qhx.admin.service.LoginService;
import com.qhx.admin.service.UserRoleService;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.to.user.PasswordNotOldTo;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.user.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 后台农户控制器(基础)
 *
 * @author: jzm
 * @date: 2024-03-07 09:05
 **/

@Component
// 以下函数参数都加roleId，表示区分,可能并没有用
public class BackUserController extends BaseController
{
    @Autowired
    protected UserRoleService userRoleService;

    @Autowired
    protected BackUserService backUserService;

    @Autowired
    protected LoginService loginService;
    


    /**
     * @param backUserQueryTo
     * @param roleId 对应查询农户的角色id
     * @return
     */
    public List<BackUser> getAll(BackUserQueryTo backUserQueryTo, int roleId){
        Integer page = backUserQueryTo.getPage();
        Integer pageSize = backUserQueryTo.getPageSize();
        List<BackUser> users = startOrderPage(page, pageSize, BackUser.class, () ->
        {
            backUserService.getAllBackUser(backUserQueryTo, roleId);
        });
        return users;
    }

    public AjaxResult getOneByUserId(Long userId){
        BackUser user = backUserService.getBackUserByUserId(userId);
        return toAjax(user);
    }

    public AjaxResult create(BackUser backUser,int roleId){
        loginService.checkAdmin();

        String password = backUser.getPassword();
        String phone = backUser.getPhone();
        if(StringUtil.isEmpty(password) || StringUtil.isEmpty(phone))
        {
            return error("新增农户失败:密码和电话为必填项!");
        }

        if(checkAddUpdate(backUser,"新增") != null){
            return checkAddUpdate(backUser,"新增");
        }
        // 插入之后更新
        boolean userEnd = backUserService.createBackUser(backUser);
        if(!userEnd)
        {
            return toAjax(userEnd);
        }
        // 插入后台农户权限
        BackUser user = backUserService.getBackUserByField(BackUser::getPhone, phone);
        boolean roleEnd = userRoleService.createUserRole(user.getUserId(), roleId);
        return toAjax(roleEnd);
    }

    public AjaxResult update(BackUser backUser){
        loginService.checkAdmin();
        if(checkAddUpdate(backUser,"修改") != null){
            return checkAddUpdate(backUser,"修改");
        }
        boolean end = backUserService.updateBackUser(backUser);
        return toAjax(end);
    }

    public AjaxResult updateMD5Password( PasswordNotOldTo passwordNotOldTo){
       loginService.checkAdmin();

        String password = passwordNotOldTo.getPassword();
        String newPassword = passwordNotOldTo.getNewPassword();
        if( StringUtil.isEmpty(newPassword) || StringUtil.isEmpty(password))
        {
            return error("重置密码失败: 农户密码不能为空");
        }
        if(!UserUtil.verifyPassword(newPassword))
        {
            return error("重置密码失败：新密码格式不对!");
        }

        if(!backUserService.isMD5PassExists(password))
        {
            return error("重置密码失败：旧密码不存在!");
        }
        if(!backUserService.checkPasswordUnique(newPassword))
        {
            return error("重置密码失败：新密码已经被其他农户注册!");
        }
        boolean end = backUserService.updateMD5Pass(passwordNotOldTo);
        return toAjax(end);
    }


    public AjaxResult delete(BackUserDeleteTo backUserDeleteTo){
       loginService.checkAdmin();
        boolean end = backUserService.deleteBackUser(backUserDeleteTo);
        return toAjax(end);
    }

    public AjaxResult updateStatus(BackUser backUser){
       //loginService.checkAdmin();
        if(!UserUtil.verifyStatus(backUser.getStatus()))
        {
            return error("修改农户状态失败：状态字符不合法");
        }
        boolean end = backUserService.updateStatus(backUser);
        return toAjax(end) ;
    }

    /**
     * 对新增、修改农户的参数校验
     * @param backUser
     * @param appMes
     * @return
     */
    private AjaxResult checkAddUpdate(BackUser backUser,String appMes)
    {
        String email = backUser.getEmail();
        String username = backUser.getUsername();

         // 限制密码校验只能是新增
        if( StringUtil.equals("新增",appMes) && !(UserUtil.verifyPassword(backUser.getPassword()))){
            return error(appMes +"农户失败：密码格式不对!");
        }
        if(!UserUtil.verifyPhone(backUser.getPhone())){
            return error(appMes+"农户失败：电话格式不对!");
        }
        if(StringUtil.equals("新增",appMes) && !backUserService.checkPasswordUnique(backUser.getPassword())){
            return error(appMes+"农户失败：密码已经被注册!");
        }
        if(StringUtil.equals("新增",appMes) && !backUserService.checkPhoneUnique(backUser)){
            // 要加上.如果查询电话 == 当前输入电话,(不报不存在错误,因为本身就是自己的,这种行为是默许的,在修改逻辑里面)
            return error(appMes+"农户失败：电话已经被注册!");
        }else if(!backUserService.checkUpPhoneUnique(backUser)) {
            return error("农户失败：电话已经被其他人注册!");
        }

        if(StringUtil.isNotEmpty(username)){
            if(!UserUtil.verifyUsername(username))
            {
                return error(appMes + "农户失败："+"用户名格式不对!");
            }
        }

        if(StringUtil.isNotEmpty(email)){

            if(!UserUtil.verifyEmail(backUser.getEmail())){
                return error(appMes+"农户失败：邮箱格式不对!");
            }
            if( StringUtil.equals("新增",appMes) && !backUserService.checkEmailUnique(backUser)){
                return error(appMes+"农户失败：邮箱已经被注册!");
            }else if(!backUserService.checkUpEmailUnique(backUser)){
                return error(appMes + "农户失败：邮箱已经被其他农户注册!");
            }
        }
        return null;
    }
}
