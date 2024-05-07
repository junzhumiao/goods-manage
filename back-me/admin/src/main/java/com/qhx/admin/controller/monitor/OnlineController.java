package com.qhx.admin.controller.monitor;

import cn.hutool.core.bean.BeanUtil;
import com.qhx.admin.model.to.OnlineUserForceTo;
import com.qhx.admin.model.to.OnlineUserQueryTo;
import com.qhx.admin.model.vo.LoginUserVo;
import com.qhx.admin.model.vo.OnlineUserVo;
import com.qhx.admin.service.LoginService;
import com.qhx.common.constant.CacheConstant;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.redis.RedisCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-28 16:23
 **/

@RestController
@RequestMapping("/monitor/online")
public class OnlineController
{
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ApiOperation("获取全部在线用户")
    public PageResult getAllUser(@RequestBody OnlineUserQueryTo to){
        // 获取全部
        List<OnlineUserVo> onlineUserVos  = getAllOnlineUser();
        // 分页
        onlineUserVos = pageList(to.getPage(),to.getPageSize(),onlineUserVos);
        // 参数过滤
        onlineUserVos =   handlerParams(onlineUserVos,to);
        return PageResult.success(onlineUserVos,onlineUserVos.size());
    }

    @RequestMapping(value = "/forceUser",method = RequestMethod.POST)
    @ApiOperation("强退用户")
    public AjaxResult  forceUser(@RequestBody OnlineUserForceTo to){
        Long userId = to.getUserId();
        if(StringUtil.isEmpty(userId)){
            return AjaxResult.success();
        }
        if(StringUtil.equals(userId,loginService.getUserId())){
            return AjaxResult.error("操作者不能强退自己账号!");
        }
        redisCache.deleteObject(CacheConstant.LOGIN_BACK_USER_KEY + userId);
        return AjaxResult.success();
    }

    private List<OnlineUserVo> handlerParams(List<OnlineUserVo> tsList, OnlineUserQueryTo to)
    {
        List<OnlineUserVo> resList = new ArrayList<>();
        if(StringUtil.isEmpty(to.getUsername()) && StringUtil.isEmpty(to.getLoginIp()))
        {
            return tsList;
        }

        for (OnlineUserVo ol : tsList)
        {
            String loginIp = to.getLoginIp();
            String username = to.getUsername();
            if(StringUtil.isEmpty(ol.getLoginIp()) && StringUtil.contains(username,ol.getUsername()))
            {
                resList.add(ol);
            } else if(StringUtil.equals(ol.getLoginIp(),loginIp) && StringUtil.isEmpty(ol.getUsername()))
            {
                resList.add(ol);
            }
           else if (StringUtil.equals(ol.getLoginIp(),loginIp) && StringUtil.contains(username,ol.getUsername()))
           {
               resList.add(ol);
           }
        }
        return resList;
    }


    private OnlineUserVo copyLoginUserToOnlineUser(LoginUserVo loginUserVo){
        OnlineUserVo onlineUserVo = new OnlineUserVo();
        BeanUtil.copyProperties(loginUserVo,onlineUserVo);
        BeanUtil.copyProperties(loginUserVo.getUser(),onlineUserVo);
        return onlineUserVo;
    }

    private List<OnlineUserVo> getAllOnlineUser(){
        Collection<String> userKeys = redisCache.keys(CacheConstant.LOGIN_BACK_USER_KEY+"*");
        List<OnlineUserVo> onlineUserVos = new ArrayList<>();
        for (String userKey : userKeys)
        {
            LoginUserVo loginUserVo = redisCache.getCacheObject(userKey);
            onlineUserVos.add(copyLoginUserToOnlineUser(loginUserVo));
        }
        return onlineUserVos;
    }

    private <T> List<T> pageList(int page, int pageSize, List<T> pList){
        List<T> list = new ArrayList<>();
        if(page == 0 || pageSize == 0){
            return list;
        }
        // 1 10 -> 0 9
        // 1 5 -> 0 4
        // 2 5 -> 5 9
        int start = (page - 1) * pageSize;
        int end = page * pageSize;
        for (int i = start; i < end ; i++)
        {
            try
            {
                T t = pList.get(i);
                list.add(t);
            }catch (Exception e){  /// 直接报异常没这么多元素
                break;
            }
        }
        return list;
    }



}
