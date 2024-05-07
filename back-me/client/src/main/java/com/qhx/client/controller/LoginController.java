package com.qhx.client.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.client.context.LoginUserContext;
import com.qhx.client.domain.BackUser;
import com.qhx.client.domain.FrontUser;
import com.qhx.client.model.to.LoginTo;
import com.qhx.client.model.to.RegisterTo;
import com.qhx.client.model.to.frontUser.InviCodeTo;
import com.qhx.client.service.FrontUserService;
import com.qhx.common.constant.CacheConstant;
import com.qhx.common.constant.Constant;
import com.qhx.common.constant.HttpStatus;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.TokenUtil;
import com.qhx.common.util.redis.RedisCache;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登录控制器
 *
 * @author: jzm
 * @date: 2024-02-27 16:51
 **/

@RestController
public class LoginController extends BaseController
{

    @Autowired
    private FrontUserService frontUserService;

    @Autowired
    private RedisCache redisCache;


    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody LoginTo loginTo){
        String password = loginTo.getPassword();
        String phone = loginTo.getPhone();
        if(StringUtil.isEmpty(password) || !UserUtil.verifyPassword(password))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }

        if(StringUtil.isEmpty(phone) || !UserUtil.verifyPhone(phone))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }
        FrontUser user = frontUserService.login(loginTo);
        if(StringUtil.isEmpty(user)){
            return HttpStatus.error("电话或密码不存在!");
        }
        // 用户状态校验
        if (UserUtil.isDelete(user.getDelFlag())) {
            return HttpStatus.USER_DELETE;
        }
        if (UserUtil.isDisable(user.getStatus())) {
            return HttpStatus.USER_BLOCKED;
        }
        // 修改登录信息
        updateLoginInfo(user);
        //缓存user到redis
        Long userId = user.getUserId();
        redisCache.setCacheObject(CacheConstant.LOGIN_FRONT_USER_KEY+userId,user,7, TimeUnit.DAYS);
        // 生成token
        String token = TokenUtil.createToken(userId);
        // 将token响应到前端
        HashMap<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user",user);
        return success(result);
    }

    @RequestMapping(path = "check/inviCode",method = RequestMethod.POST)
    @ApiOperation("校验邀请码")
    public AjaxResult checkInviCode(@RequestBody InviCodeTo inviCodeTo){
        String code = inviCodeTo.getInvitationCode();
        if(StringUtil.isNotEmpty(inviCodeTo.getInvitationCode()))
        {
            BackUser backUser = frontUserService.isBindBackUser(code.substring(Constant.In_Code_Pre_Len));
            if(Objects.isNull(backUser))
            {
                return error("输入邀请码无效!");
            }
            return success("您的后台用户有注册账户!您是否同意将后台数据同步到到当前系统?");
        }
        return error();
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public AjaxResult register(@RequestBody RegisterTo registerTo){
        // 校验邀请码
        String code = registerTo.getInvitationCode();
        if(StringUtil.isNotEmpty(code))
        {
            BackUser backUser = frontUserService.isBindBackUser(code.substring(Constant.In_Code_Pre_Len));
            if(StringUtil.isNotEmpty(backUser))
            {
                FrontUser frontUser = new FrontUser();
                // 校验邀请用户是否注册过
                if(!frontUserService.checkPasswordUnique(backUser.getPassword())
                        || !frontUserService.checkPhoneUnique(backUser.getPhone())
                        || !frontUserService.checkEmailUnique(backUser.getEmail()))
                {
                    return error("该邀请码所属用户已经注册过!");
                }
                BeanUtil.copyProperties(backUser,frontUser); // 拷贝值
                updateLoginInfo(frontUser);
                boolean end = frontUserService.createFrontUser(frontUser);
                return toAjax(end);
            }
        }
        // 邀请码 -- 是商家用户
        String password = registerTo.getPassword();
        String phone = registerTo.getPhone();
        String username = registerTo.getUsername();
        if(StringUtil.isEmpty(password) || !UserUtil.verifyPassword(password))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }

        if(StringUtil.isEmpty(phone) || !UserUtil.verifyPhone(phone))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }
        if(StringUtil.isNotEmpty(username))
        {
            if(!UserUtil.verifyUsername(username))
            {
                return HttpStatus.USERNAME_NOT_MATCH;
            }
        }
        // 校验密码和电话是否唯一
        if(!frontUserService.checkPasswordUnique(password))
        {
            return HttpStatus.PASSWORD_EXISTS;
        }

        if(!frontUserService.checkPhoneUnique(phone)){
            return HttpStatus.PHONE_EXISTS;
        }
        boolean end =  frontUserService.createFrontUser(registerTo);
        return toAjax(end);
    }


    @RequestMapping(path = "/getUserInfo")
    public AjaxResult getUserInfo(){
        FrontUser user = LoginUserContext.getUser();
        return success(user);
    }

    // 修改登录信息
    private void updateLoginInfo(FrontUser user)
    {
        // 设置登录者的真实ip地址
        UserUtil.updateLoginInfo(user);
        Long userId = user.getUserId();
        LambdaQueryWrapper<FrontUser> updateWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(FrontUser::getUserId, userId);
        frontUserService.update(user,updateWrapper);
    }

}
