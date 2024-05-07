package com.qhx.admin.controller.system.backUser;

import cn.hutool.core.bean.BeanUtil;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.Shop;
import com.qhx.admin.model.to.backUser.BackUserDeleteTo;
import com.qhx.admin.model.to.backUser.BackUserQueryTo;
import com.qhx.admin.model.vo.FarmerVo;
import com.qhx.admin.service.ShopService;
import com.qhx.common.constant.Constant;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.model.to.user.PasswordNotOldTo;
import com.qhx.common.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  农户角色控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/farmer")
public class FarmerController
{
    @Autowired
    private  BackUserController backUserController;

    @Autowired
    private ShopService shopService;

    // 目前是在不清楚对于日期解析,为什么get解析会报错。post可以
    @RequestMapping(path = "/get/all",method = RequestMethod.POST)
    public PageResult getAll(@RequestBody BackUserQueryTo backUserQueryTo){
        List<BackUser> users = backUserController.getAll(backUserQueryTo, Constant.Role_Farmer);
        List<FarmerVo> farmerVos = BeanUtil.copyToList(users, FarmerVo.class);
        for (FarmerVo farmerVo : farmerVos)
        {
            Shop shop = shopService.getShop(farmerVo.getUserId());
            if(StringUtil.isNotEmpty(shop)){
                farmerVo.setShop(true);
            }
        }
        return PageResult.success(farmerVos,farmerVos.size());
    }

    @RequestMapping(path = "/get/one",method = RequestMethod.GET)
    @ApiOperation("根据id查询农户详细信息")
    public AjaxResult getOneByUserId(Long userId){
      return backUserController.getOneByUserId(userId);
    }
    
    @RequestMapping(path = "/create",method = RequestMethod.POST)
    @ApiOperation("创建农户")
    public AjaxResult create(@RequestBody BackUser backUser){
        // 头像图片,需要用户登录之后自己上传
       return backUserController.create(backUser,Constant.Role_Farmer);
    }

    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改农户信息")
    public AjaxResult update(@RequestBody BackUser backUser){
       return backUserController.update(backUser);
    }

    @RequestMapping(path = "/update/pass",method = RequestMethod.POST)
    @ApiOperation("修改农户密码")
    public AjaxResult updatePassword(@RequestBody PasswordNotOldTo passwordNotOldTo){
        return backUserController.updateMD5Password(passwordNotOldTo);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation("删除农户")
    public AjaxResult delete(@RequestBody BackUserDeleteTo backUserDeleteTo){
       return backUserController.delete(backUserDeleteTo);
    }

    @RequestMapping(path = "/update/status",method = RequestMethod.POST)
    @ApiOperation("修改农户账户状态")
    public AjaxResult updateStatus(@RequestBody BackUser backUser){
     return backUserController.updateStatus(backUser);
    }

}
