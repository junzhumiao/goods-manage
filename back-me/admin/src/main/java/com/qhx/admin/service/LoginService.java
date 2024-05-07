package com.qhx.admin.service;

import com.qhx.admin.domain.BackUser;
import com.qhx.admin.model.vo.LoginUserVo;
import com.qhx.common.constant.*;
import com.qhx.common.exception.PermissionException;
import com.qhx.common.util.DateUtil;
import com.qhx.common.util.ServletUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.redis.RedisCache;
import com.qhx.common.util.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *  登录校验
 *
 * @author: jzm
 * @date: 2024-03-07 17:09
 **/
@Service
public class LoginService
{
    @Autowired
    private RedisCache redisCache;

    public Long getUserId(){
        LoginUserVo loginUserVo = SecurityUtil.getPrincipal();
        if(StringUtil.isEmpty(loginUserVo)){
            return null;
        }
        return loginUserVo.getUser().getUserId();
    }

    /**
     * 是否记住密码状态下
     */
    public  final boolean  isRememberPass(){
        String status = ServletUtil.getRequest().getHeader(HeaderConstant.REMEMBER_PASS);
        if(StringUtil.isEmpty(status))
        {
            return false;
        }
        if(status.equals("true"))
        {
            return true;
        }
        return false;
    }


    /**
     * 校验登录用户是否是管理员 (不是，直接抛出异常)
     */
    public final  void checkAdmin()
    {
       if(isAdmin()){
           return;
       }
        throw new PermissionException();
    }

    /**
     * 校验登录用户是否是管理员
     */
    public final  boolean isAdmin()
    {
        LoginUserVo loginUserVo = SecurityUtil.getPrincipal();
        if(StringUtil.isNotEmpty(loginUserVo))
        {
            if(loginUserVo.isAdmin()){
                return true;
            }
        }
        return false;
    }



    /**
     * 校验验证码
     */
    public  final HttpStatus verifyCaptcha(){
        String codeVal = ServletUtil.getHeader(HeaderConstant.CAPTCHA_VAL);
        String codeKey = ServletUtil.getHeader(HeaderConstant.CAPTCHA_KEY);

        if(codeVal != null){
            codeKey = CacheConstant.CAPTCHA_CODE_KEY + codeKey;
            String val = redisCache.getCacheObject(codeKey);
            if(val == null) { // 验证码过期
                return HttpStatus.CAPTCHA_EXPIRED;
            }
            if(!StringUtil.equals(val,codeVal)){  // 验证码不一致
                return HttpStatus.CAPTCHA_CHECK_FAILED;
            }
            return null;
        }
        return HttpStatus.CAPTCHA_CHECK_FAILED;
    }

    public final HttpStatus checkPassIsLocked(){
        String clientId = ServletUtil.getHeader(HeaderConstant.XCliId);
        String clientMaxKey = CacheConstant.PWD_ERR_MAX_CNT_KEY + clientId;
        long expire = redisCache.getExpire(clientMaxKey);
        if(expire >0)
        {
            return HttpStatus.PASSWORD_RETRY_LIMIT_EXCEED(DateUtil.parseMinS(expire));
        }
        return null;

    }

    public final HttpStatus checkPassRetryErrorCount(BackUser user)
    {
        // 密码错误验证时间： 5 min
        // 最大密码错误次数,锁定时间：10 min
        Integer liveTime = UserConstant.LiveTime;
        Integer pwdBlockedTime =  UserConstant.PwdBlockedTime;
        if(user == null)
        {
            String clientId = ServletUtil.getHeader(HeaderConstant.XCliId);
            String clientKey = CacheConstant.PWD_ERR_REM_CNT_KEY + clientId;
            String clientMaxKey = CacheConstant.PWD_ERR_MAX_CNT_KEY + clientId;

            if(StringUtil.isNotEmpty(clientId))
            {
                // 校验是否输入密码错误被锁定
                long expire = redisCache.getExpire(clientMaxKey);
                if(expire >0)
                {
                    return HttpStatus.PASSWORD_RETRY_LIMIT_EXCEED(DateUtil.parseMinS(expire));
                }

                Integer errCnt = redisCache.getCacheObject(clientKey);
                if(Objects.equals(errCnt, 0)) // 剩余密码输入错误次数 = 0
                {
                    redisCache.setCacheObject(clientMaxKey,"true",pwdBlockedTime, TimeUnit.MINUTES);
                    return HttpStatus.PASSWORD_RETRY_LIMIT_EXCEED(pwdBlockedTime + ":00");
                }
                if(StringUtil.isEmpty(errCnt))
                {
                    redisCache.setCacheObject(clientKey, Constant.PWD_ERR_MAX_CNT -1,liveTime, TimeUnit.MINUTES);
                    return HttpStatus.PASSWORD_REMAIN_INPUT_COUNT(Constant.PWD_ERR_MAX_CNT -1);
                }else{
                    redisCache.setCacheObject(clientKey,errCnt - 1,liveTime,TimeUnit.MINUTES);
                    return HttpStatus.PASSWORD_REMAIN_INPUT_COUNT( errCnt - 1);
                }
            }
            return HttpStatus.REQ_HED_PAM_NOT_ENOUGH;
        }
        return null;
    }
}
