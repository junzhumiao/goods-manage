package com.qhx.admin.config.security.filter;

import cn.hutool.json.JSONUtil;
import com.qhx.admin.model.vo.LoginUserVo;
import com.qhx.common.constant.*;
import com.qhx.common.util.ServletUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.TokenUtil;
import com.qhx.common.util.redis.RedisCache;
import com.qhx.common.util.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 *
 * @author: qhx20040819
 * @date: 2023-09-09 21:27
 **/
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;


    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 设置跨域
        response.setHeader("Access-Control-Allow-Origin","*"); // 修改携带cookie,PS
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        // 预检请求缓存时间（秒），即在这个时间内相同的预检请求不再发送，直接使用缓存结果。
        response.setHeader("Access-Control-Max-Age", "3600");
        // 设置允许的设置自定义响应头
        response.setHeader("Access-Control-Expose-Headers", HeaderConstant.REFRESH_TOKEN);

        String path = request.getRequestURI();
        // 排除不过滤列表
        if(isExclusionList(path, UserConstant.ExclusionList)){
            filterChain.doFilter(request,response);
            return;
        }
        // token拿取和校验
        String authorization = request.getHeader(HeaderConstant.AUTHORIZATION);
        if(StringUtil.isEmpty(authorization)){
            ServletUtil.renderString(response,JSONUtil.toJsonStr(HttpStatus.USER_NOT_LOGIN));
            return;
        }

        if (!authorization.startsWith(Constant.TOKEN_PREFIX))
        {
            ServletUtil.renderString(response,JSONUtil.toJsonStr(HttpStatus.USER_TOKEN_ILLICIT));
            return;
        }

        String token = authorization.substring(Constant.TOKEN_PREFIX.length());


        // 校验用户登录过期
        String userId = TokenUtil.parseTokenGetUserId(token);
        String loginKey = CacheConstant.LOGIN_BACK_USER_KEY + userId;
        LoginUserVo userVo =  redisCache.getCacheObject(loginKey);

        if(StringUtil.isNull(userVo)){
            ServletUtil.renderString(response, JSONUtil.toJsonStr(HttpStatus.USER_LOGIN_EXPIRED));
            return;
        }

        // 用户离线校验
        token =  TokenUtil.verifyRefresh(token);
        if(StringUtil.isEmpty(token)) // 默认离线超过20min不刷新令牌
        {
            redisCache.deleteObject(CacheConstant.LOGIN_BACK_USER_KEY+userId);
            ServletUtil.renderString(response,JSONUtil.toJsonStr(HttpStatus.OFF_LIMIT_EXCEEDED));
            return;
        }
        // 响应刷新令牌
        response.setHeader(HeaderConstant.REFRESH_TOKEN,token);
        //获取权限信息封装到Authentication中,开始就缓存了userVo对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userVo,null,userVo.getAuthorities());
        SecurityUtil.setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }


    // 排除不拦截列表
    private boolean isExclusionList(String path,String ...exclusionList){
        for (String ep : exclusionList)
        {
            if (ep.equals(path)){
                return true;
            }
            // 这是 /path/qhx => /path/**
            if(ep.contains("/**")){
                int len = ep.length();
                ep = ep.substring(0,len - "/**".length());
                if (path.contains(ep)){
                    return true;
                }
            }
        }
        return false;
    }


}
