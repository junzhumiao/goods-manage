package com.qhx.client.context;

import com.qhx.client.domain.FrontUser;

/**
 * @author: jzm
 * @date: 2024-03-17 11:40
 **/

public class LoginUserContext
{
    private static ThreadLocal<FrontUser> local = new ThreadLocal<>();

    public static void setUser(FrontUser user)
    {
        local.set(user);
    }

    public static FrontUser getUser()
    {
        return  local.get();
    }

}
