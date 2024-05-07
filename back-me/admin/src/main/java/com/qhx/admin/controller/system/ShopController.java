package com.qhx.admin.controller.system;

import com.qhx.admin.domain.Shop;
import com.qhx.admin.service.LoginService;
import com.qhx.admin.service.ShopService;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家控制器(是后台用户个人信息修改的附属展示。)
 *
 * @author: jzm
 * @date: 2024-03-19 21:13
 **/

@RestController
@RequestMapping("/shop")
// 商家是农户的附加账号
public class ShopController extends BaseController
{

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShopService shopService;


    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改商家信息")
    public AjaxResult updateShop(@RequestBody Shop shop){
        boolean end = shopService.updateShop(shop);
        return toAjax(end);
    }



    @RequestMapping(path = "/get/one" ,method = RequestMethod.GET)
    @ApiOperation("获取商家信息")
    public AjaxResult getShop(){
        Long userId = loginService.getUserId();
        return toAjax(shopService.getShop(userId));
    }

    @RequestMapping(path = "/open",method = RequestMethod.POST)
    @ApiOperation("开通商家信息")
    public AjaxResult openShop(@RequestBody Shop shop){
        shop.setUserId(loginService.getUserId());
        if(StringUtil.isEmpty(shop.getShopName())){
            return error("商家名称不能为空!");
        }
        boolean end  =  shopService.createShop(shop);
        return AjaxResult.success(end);
    }

    @RequestMapping(path = "/update/avatar",method = RequestMethod.POST)
    @ApiOperation("修改商家头像")
    public AjaxResult updateShopAvatar(@RequestBody Shop shop){
        if(StringUtil.isEmpty(shop.getShopAvatar()) || !UserUtil.checkAvatar(shop.getShopAvatar())){
           return error("上传头像地址不合法!");
        }
        boolean end  = shopService.updateShopAvatar(shop);
        return AjaxResult.success(end);
    }
}
