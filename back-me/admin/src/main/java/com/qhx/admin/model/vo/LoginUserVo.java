package com.qhx.admin.model.vo;


import com.alibaba.fastjson2.annotation.JSONField;
import com.qhx.admin.domain.BackUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户信息响应类
 *
 * @author: jzm
 * @date: 2024-02-28 15:23
 **/

@Getter
@Setter
public class LoginUserVo  implements UserDetails
{
    private BackUser user;

    private List<MenuVo> menus;

    private boolean isAdmin;

    private String browserName;
    private String osName;
    private String tokenId;
    private String loginLocation; // 登录地址

    private List<String> authList;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;  // SimpleGrantedAuthority对象不支持序列化，无法存入redis


    public LoginUserVo(BackUser user, List<String> authList,List<MenuVo> menuVos) { // 将对应的权限字符串列表传入
        this.user = user;
        this.authList = authList;
        this.menus = menuVos;
    }



    @Override // TODO 我写上是为了可复用，但我实际上没有用到权限管理
    public Collection<? extends GrantedAuthority> getAuthorities()
    {

        if(authorities != null){
            return authorities;
        }else{
            authorities = new ArrayList<>();
        }
        // 第一次登录，封装UserDetails对象,初始化权限列表
     if(authList != null){
         for (String auth : authList) {
             SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
             authorities.add(simpleGrantedAuthority); //默认是个空的
         }
        }
        return authorities;

    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}