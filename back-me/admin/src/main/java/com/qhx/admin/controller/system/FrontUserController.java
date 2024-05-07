package com.qhx.admin.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.PageInfo;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.FrontUser;
import com.qhx.admin.model.to.frontUser.FrontUserDeleteTo;
import com.qhx.admin.model.to.frontUser.FrontUserQueryTo;
import com.qhx.admin.model.to.frontUser.InviCodeTo;
import com.qhx.admin.service.FrontUserService;
import com.qhx.admin.service.LoginService;
import com.qhx.common.constant.Constant;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.model.to.user.PasswordNotOldTo;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@Api("前台用户管理")
@RestController
@RequestMapping("/front/user")
public class FrontUserController extends BaseController
{
    @Autowired
    private FrontUserService frontUserService;

    @Autowired
    private LoginService loginService;


    @RequestMapping(path = "/get/all",method = RequestMethod.POST)
    @ApiOperation("查询全部用户详细信息")
    public PageResult getAll(@RequestBody  FrontUserQueryTo frontUserQueryTo){

        Integer page = frontUserQueryTo.getPage();
        Integer pageSize = frontUserQueryTo.getPageSize();
        PageInfo pageInfo = startPage(page, pageSize, () ->
        {
            frontUserService.getAllFrontUser(frontUserQueryTo);
        });
        return toAjax(pageInfo);
    }


    @RequestMapping(path = "/get/one",method = RequestMethod.GET)
    @ApiOperation("根据id查询用户详细信息")
    public AjaxResult getOneByUserId(Long userId){
       FrontUser user =  frontUserService.getOneByUserId(userId);
       return toAjax(user);
    }


    @RequestMapping(path = "/get/one/phone",method = RequestMethod.GET)
    @ApiOperation("根据手机号查询用户详细信息")
    public AjaxResult getOneByPhone(String phone){
        FrontUser user =  frontUserService.getFrontUserByField(FrontUser::getPhone,phone);
        return toAjax(user);
    }

    @RequestMapping(path = "/check/inviCode",method = RequestMethod.POST)
    @ApiOperation("校验邀请码")
    public AjaxResult checkInviCode(@RequestBody InviCodeTo inviCodeTo){
        String code = inviCodeTo.getInvitationCode();
        if(StringUtil.isNotEmpty(inviCodeTo.getInvitationCode()))
        {
            BackUser backUser = frontUserService.isBindBackUser(code.substring(Constant.In_Code_Pre_Len));
            if(Objects.isNull(backUser))
            {
                return error("输入前台用户邀请码无效!");
            }
            return success();
        }
        return error();
    }


    @RequestMapping(path = "/create",method = RequestMethod.POST)
    @ApiOperation("创建用户")
    public AjaxResult create(@RequestBody FrontUser frontUser){
       loginService.checkAdmin();
        String code = frontUser.getInvitationCode();
        if(StringUtil.isNotEmpty(code))
        {
            BackUser backUser = frontUserService.isBindBackUser(code.substring(Constant.In_Code_Pre_Len));
            if(StringUtil.isNotEmpty(backUser))
            {
                BeanUtil.copyProperties(backUser,frontUser); // 拷贝值

                if(!frontUserService.checkPasswordUnique(backUser.getPassword())
                        || !frontUserService.checkPhoneUnique(frontUser)
                        || !frontUserService.checkEmailUnique(frontUser))
                {
                    return error("该邀请码所属用户已经注册过!");
                }

                boolean end = frontUserService.createFrontUser(frontUser);
                return toAjax(end);
            }
        }

        String password = frontUser.getPassword();
        String phone =  frontUser.getPhone();
        if(StringUtil.isEmpty(password) || StringUtil.isEmpty(phone))
        {
            return error("新增用户失败:密码和电话为必填项!");
        }
        if(checkAddUpdate(frontUser,"新增") != null){
            return checkAddUpdate(frontUser,"新增");
        }
        // 插入之后更新
        boolean userEnd = frontUserService.createFrontUser(frontUser);
        return toAjax(userEnd);
    }


    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改用户信息")
    public AjaxResult update(@RequestBody FrontUser frontUser){
       loginService.checkAdmin();
        if(checkAddUpdate(frontUser,"修改") != null){
            return checkAddUpdate(frontUser,"修改");
        }
        boolean end = frontUserService.updateFrontUser(frontUser);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/pass",method = RequestMethod.POST)
    @ApiOperation("修改用户密码")
    public AjaxResult updatePassword(@RequestBody PasswordNotOldTo passwordNotOldTo){
        loginService.checkAdmin();
        String errPre = "重置密码失败：";
        String password = passwordNotOldTo.getPassword();
        String newPassword = passwordNotOldTo.getNewPassword();
        String md5NewPass = MD5.create().digestHex(newPassword);
        if(StringUtil.isEmpty(newPassword) || StringUtil.isEmpty(password))
        {
            return error(errPre+"用户密码不能为空");
        }
        if(!UserUtil.verifyPassword(newPassword))
        {
            return error(errPre+"新密码格式不对!");
        }
        if(password.equals(md5NewPass)){ // 新旧密码相等,不用执行下面逻辑
            return success();
        }

        if(!frontUserService.isMD5PassExists(password))
        {
            return error(errPre+"旧密码不存在!");
        }
        // 校验密码唯一性,不唯一返回false
        if(!frontUserService.checkPasswordUnique(newPassword))
        {
            return error(errPre+"新密码已经被其他用户注册!");
        }
        boolean end = frontUserService.updateMD5Pass(passwordNotOldTo);
        return toAjax(end);
    }


    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation("删除用户")
    public AjaxResult delete(@RequestBody FrontUserDeleteTo frontUserDeleteTo){
        loginService.checkAdmin();
        boolean end = frontUserService.deleteFrontUser(frontUserDeleteTo);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/status",method = RequestMethod.POST)
    @ApiOperation("修改用户账户状态")
    public AjaxResult updateStatus(@RequestBody FrontUser frontUser){
       loginService.checkAdmin();
        if(!UserUtil.verifyStatus(frontUser.getStatus()))
        {
            return error("修改农户状态失败：状态字符不合法");
        }
        boolean end = frontUserService.updateStatus(frontUser);
        return toAjax(end) ;
    }
    

    private AjaxResult checkAddUpdate(FrontUser frontUser, String appMes)
    {
        String email = frontUser.getEmail();
        String username = frontUser.getUsername();
        if( StringUtil.equals("新增",appMes) && !(UserUtil.verifyPassword(frontUser.getPassword()))){
            return error(appMes +"用户失败：密码格式不对!");
        }
        if(!UserUtil.verifyPhone(frontUser.getPhone())){
            return error(appMes+"用户失败：电话格式不对!");
        }
        if(StringUtil.equals("新增",appMes) && !frontUserService.checkPasswordUnique(frontUser.getPassword())){
            return error(appMes+"用户失败：密码已经被注册!");
        }
        if(StringUtil.equals("新增",appMes) && !frontUserService.checkPhoneUnique(frontUser)){
            return error(appMes+"用户失败：电话已经被注册!");
        }else if(!frontUserService.checkUpPhoneUnique(frontUser)){
            return error(appMes + "用户失败：电话已经被其他人注册！");
        }

        // 用户名校验
        if(StringUtil.isNotEmpty(username)){
            if(!UserUtil.verifyUsername(username))
            {
                return error(appMes + "用户失败："+"用户名格式不对!");
            }
        }

        if(StringUtil.isNotEmpty(email)){

            if(!UserUtil.verifyEmail(frontUser.getEmail())){
                return error(appMes+"用户失败：邮箱格式不对!");
            }
            if(StringUtil.equals("新增",appMes) && !frontUserService.checkEmailUnique(frontUser)){
                return error(appMes+"用户失败：邮箱已经被注册!");
            }else if(!frontUserService.checkUpEmailUnique(frontUser)){
                return error(appMes+"用户失败：邮箱已经被其他用户注册!");
            }
        }

        return null;
    }


}
