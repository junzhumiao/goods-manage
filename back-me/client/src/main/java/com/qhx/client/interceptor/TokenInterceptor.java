package com.qhx.client.interceptor;

import cn.hutool.json.JSONUtil;
import com.qhx.client.context.LoginUserContext;
import com.qhx.client.domain.FrontUser;
import com.qhx.common.constant.CacheConstant;
import com.qhx.common.constant.Constant;
import com.qhx.common.constant.HeaderConstant;
import com.qhx.common.constant.HttpStatus;
import com.qhx.common.util.ServletUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.TokenUtil;
import com.qhx.common.util.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jzm
 * @date: 2024-03-17 11:25
 **/

@Component
public class TokenInterceptor implements HandlerInterceptor
{
    @Autowired
    private RedisCache redisCache;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader(HeaderConstant.AUTHORIZATION);
        // 这个跟后台不一样，后台为空，证明没登录，后台一点不让看,这个是允许访问的
        if(StringUtil.isNotEmpty(authorization))
        {
            if(!authorization.startsWith(Constant.TOKEN_PREFIX)){
                ServletUtil.renderString(response, JSONUtil.toJsonStr(HttpStatus.USER_TOKEN_ILLICIT));
               return false;
            }
            String token = authorization.substring(Constant.TOKEN_PREFIX.length());
            String userId = TokenUtil.parseTokenGetUserId(token);
            FrontUser user = redisCache.getCacheObject(CacheConstant.LOGIN_FRONT_USER_KEY + userId);
            if(StringUtil.isEmpty(user)) // 校验登录过期
            {
                ServletUtil.renderString(response,JSONUtil.toJsonStr(HttpStatus.USER_LOGIN_EXPIRED));
                return false;
            }
            // 缓存FrontUser
            LoginUserContext.setUser(user);
        }
        return true;
    }

}
