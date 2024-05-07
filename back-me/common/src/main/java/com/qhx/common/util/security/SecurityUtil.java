package com.qhx.common.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * spring security 的工具类、相关安全
 *
 * @author: jzm
 * @date: 2024-03-01 11:05
 **/

public class SecurityUtil
{

    public static void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public  static <T> T getPrincipal(){
        return (T)getAuthentication().getPrincipal();
    }


    private static Authentication getAuthentication(){
      return   SecurityContextHolder.getContext().getAuthentication();
    }

}
