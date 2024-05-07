package com.qhx.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.Menu;
import com.qhx.admin.model.to.LoginTo;
import com.qhx.admin.model.vo.LoginUserVo;
import com.qhx.admin.model.vo.MenuVo;
import com.qhx.admin.service.BackUserService;
import com.qhx.admin.service.LoginService;
import com.qhx.admin.service.UserRoleService;
import com.qhx.common.annotation.DataSource;
import com.qhx.common.constant.CacheConstant;
import com.qhx.common.constant.Constant;
import com.qhx.common.constant.HttpStatus;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.to.user.PassTo;
import com.qhx.common.util.ServletUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.TokenUtil;
import com.qhx.common.util.ip.AddressUtil;
import com.qhx.common.util.ip.IpUtil;
import com.qhx.common.util.redis.RedisCache;
import com.qhx.common.util.security.SecurityUtil;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: jzm
 * @date: 2024-02-27 09:05
 **/

@RestController
public class LoginController extends BaseController
{


    @Autowired
    private BackUserService backUserService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisCache redisCache;


    @DataSource
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "captcha-val", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "captcha-key", value = "验证码键(调用/get/captcha返回)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remember-pass", value = "是否重复记住密码键", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "X-Client-Id", value = "设备唯一键(uuid)", required = true, dataType = "String")
    })
    public AjaxResult login(@RequestBody LoginTo loginTo)
    {
        // 检查密码错误被锁定
        HttpStatus passStatus = loginService.checkPassIsLocked();
        if (StringUtil.isNotEmpty(passStatus))
        {
            return passStatus;
        }
        String password = loginTo.getPassword();
        String username = loginTo.getUsername();
        // 验证码校验
        HttpStatus status = loginService.verifyCaptcha();
        if (status != null)
        {
            return status;
        }
        // 密码或用户名为null
        if (StringUtil.isEmpty(password) || StringUtil.isEmpty(username))
        {
            return HttpStatus.PASSWORD_OR_USERNAME_NOT_EMPTY;
        }
        if (!UserUtil.verifyPassword(password))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }
        if (!UserUtil.verifyUsername(username))
        {
            return HttpStatus.USERNAME_NOT_MATCH;
        }
        // 查询用户
        BackUser user = backUserService.login(loginTo);
        if (StringUtil.isEmpty(user)) // user==null,用户名和密码错误
        {
            return HttpStatus.USER_NOT_EXISTS;
        }
        // 密码错误次数校验
        HttpStatus httpStatus = loginService.checkPassRetryErrorCount(user);
        if (httpStatus != null)
        {
            return httpStatus;
        }
        // 用户状态校验 mybatis-plus默认忽略逻辑删除字段，由于采用mybatis-plus逻辑删除,因此查询自动忽略已经删除字段
        //if (UserUtil.isDelete(user.getDelFlag())) {
        //    return HttpStatus.USER_DELETE;
        //}

        if (UserUtil.isDisable(user.getStatus()))
        {
            return HttpStatus.USER_BLOCKED;
        }
        // 修改登录信息
        updateLoginInfo(user);
        Long userId = user.getUserId();
        // 生成token
        String token = TokenUtil.createToken(userId);
        // 缓存userVo
        List<MenuVo> menuVos = menuCovertMenuVo(backUserService.getMenuByUserId(userId));
        LoginUserVo userVo = new LoginUserVo(user, null, menuVos);
        userVo.setTokenId(token);
        updateLoginUserInfo(userVo);
        // 是否记住密码状态下
        if (loginService.isRememberPass())
        {
            redisCache.setCacheObject(CacheConstant.LOGIN_BACK_USER_KEY + userId, userVo, 7, TimeUnit.DAYS);
        } else
        {
            redisCache.setCacheObject(CacheConstant.LOGIN_BACK_USER_KEY + userId, userVo, 1, TimeUnit.HOURS);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", userVo);
        return AjaxResult.success(result);
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public AjaxResult register(@RequestBody BackUser backUser)
    {
        if (!StringUtil.isAllNotEmpty(backUser.getPassword(), backUser.getPhone()))
        {
            return HttpStatus.PASSWORD_OR_PHONE_NOT_EMPTY;
        }
        if (!UserUtil.verifyPassword(backUser.getPassword()))
        {
            return HttpStatus.PASSWORD_NOT_MATCH;
        }
        if (!UserUtil.verifyPhone(backUser.getPhone()))
        {
            return HttpStatus.PHONE_NOT_MATCH;
        }
        if (!backUserService.checkPasswordUnique(backUser.getPassword()))
        {
            return HttpStatus.PASSWORD_EXISTS;
        }
        if (!backUserService.checkPhoneUnique(backUser))
        {
            return HttpStatus.PHONE_EXISTS;
        }
        boolean end = backUserService.createBackUser(backUser);
        BackUser user = backUserService.getBackUserByField(BackUser::getPhone, backUser.getPhone());
        userRoleService.createUserRole(user.getUserId(), Constant.Role_Farmer);
        return toAjax(end);
    }

    @RequestMapping(path = "/get/user", method = RequestMethod.GET)
    public AjaxResult getUserInfo()
    {
        LoginUserVo userVo = SecurityUtil.getPrincipal();
        return AjaxResult.success(userVo);
    }

    @RequestMapping(path = "/update/pass", method = RequestMethod.POST)
    @ApiOperation("用户修改密码")
    public AjaxResult updatePassword(@RequestBody PassTo passTo)
    {
        //
        String oldPassword = passTo.getOldPassword();
        String newPassword = passTo.getNewPassword();
        String errPre = "";
        if (StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword))
        {
            return error(errPre + "用户密码不能为空");
        }
        if (!UserUtil.verifyPassword(oldPassword))
        {
            return error(errPre + "旧密码格式不对!");
        }
        if (!UserUtil.verifyPassword(newPassword))
        {
            return error(errPre + "新密码格式不对!");
        }
        if (StringUtil.equals(newPassword, oldPassword))
        {
            return success();
        }
        if (!backUserService.isPasswordExists(oldPassword))
        {
            return error(errPre + "旧密码不存在!");
        }
        if (!backUserService.checkPasswordUnique(newPassword))
        {
            return error(errPre + "新密码已经被其他用户注册!");
        }
        boolean end = backUserService.updatePassword(passTo);
        BackUser backUser = backUserService.getBackUserByField(BackUser::getPassword, MD5.create().digest(newPassword));
        updateUserInfoRedis(backUser);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/user", method = RequestMethod.POST)
    @ApiOperation("用户修改信息")
    public AjaxResult updateUser(@RequestBody BackUser backUser)
    {
        String nickname = backUser.getNickname();
        String phone = backUser.getPhone();
        String email = backUser.getEmail();
        if (!StringUtil.isAllNotEmpty(nickname, phone, email))
        {
            return error("用户昵称、电话、邮箱 为必填项!");
        } else if (!UserUtil.verifyUsername(nickname))
        {
            return error("用户昵称格式不正确!");
        } else if (!UserUtil.verifyPhone(phone))
        {
            return error("电话格式不正确!");
        } else if (!UserUtil.verifyEmail(email))
        {
            return error("邮箱格式不正确!");
        } else if (!backUserService.checkUpPhoneUnique(backUser))
        {
            return error("电话已经被其他账户绑定!");
        } else if (!backUserService.checkUpEmailUnique(backUser))
        {
            return error("邮箱已经被其他账户绑定!");
        }
        boolean end = backUserService.updateBackUser(backUser);
        updateUserInfoRedis(backUser);
        return toAjax(end);
    }


    @RequestMapping(path = "/update/avatar", method = RequestMethod.POST)
    @ApiOperation("用户修改头像")
    public AjaxResult updateAvatar(@RequestBody BackUser backUser)
    {
        boolean end = backUserService.updateAvatar(backUser);
        updateUserInfoRedis(backUser);
        return toAjax(end);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public AjaxResult logout()
    {
        AjaxResult result = AjaxResult.success();
        LoginUserVo userVo = SecurityUtil.getPrincipal();
        if (StringUtil.isNotNull(userVo))
        {
            BackUser user = userVo.getUser();
            // 删除用户缓存记录
            String cacheKy = CacheConstant.LOGIN_BACK_USER_KEY + user.getUserId();
            redisCache.deleteObject(cacheKy);
            // 记录用户退出日志
            result.setMes("用户退出成功");
            return result;
        }
        result.setMes("用户退出成功!");
        return result;
    }


    private void updateLoginUserInfo(LoginUserVo userVo)
    {
        String ua = ServletUtil.getRequest().getHeader(Header.USER_AGENT.getValue());
        UserAgent userAgent = UserAgentUtil.parse(ua);
        Browser browser = userAgent.getBrowser();
        userVo.setBrowserName(browser.getName() + browser.getVersion(ua));
        userVo.setOsName(userAgent.getOs().getName());
        userVo.setLoginLocation(AddressUtil.getRealLocation(IpUtil.getIpAddr()));
        userVo.setAdmin(userRoleService.isAdmin(userVo.getUser().getUserId()));
    }

    // 修改登录信息
    private void updateLoginInfo(BackUser user)
    {
        // 设置登录者的真实ip地址
        String requestIp = IpUtil.getIpAddr();
        String address = AddressUtil.getRealAddressByIP(requestIp);

        user.setLoginIp(address);
        user.setLoginDate(LocalDateTime.now());

        Long userId = user.getUserId();
        LambdaQueryWrapper<BackUser> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(BackUser::getUserId, userId);
        backUserService.update(user, updateWrapper);
    }


    private List<MenuVo> menuCovertMenuVo(List<Menu> menus)
    {
        List<MenuVo> menuVos = BeanUtil.copyToList(menus, MenuVo.class);
        List<MenuVo> rootMenuVos = new ArrayList<>();
        // 找到根菜单
        for (MenuVo menuVo : menuVos)
        {
            if (menuVo.getRootId() == -1)
            {
                rootMenuVos.add(menuVo);
            }
        }
        // 将子菜单放到根菜单
        for (MenuVo rMenuVo : rootMenuVos)
        {
            for (MenuVo menuVo : menuVos)
            {
                if (Objects.equals(rMenuVo.getMenuId(), menuVo.getRootId()))
                {
                    if (rMenuVo.getChildren() == null)
                        rMenuVo.setChildren(new ArrayList<>());
                    rMenuVo.getChildren().add(menuVo);
                }
            }
        }
        return rootMenuVos;

    }


    /**
     * 修改UserInfo在redis中的数据
     *
     * @param user
     */
    public void updateUserInfoRedis(BackUser user)
    {
        Long userId = user.getUserId();
        String redisKey = CacheConstant.LOGIN_BACK_USER_KEY + userId;
        LoginUserVo loginUserVo = redisCache.getCacheObject(CacheConstant.LOGIN_BACK_USER_KEY + userId);

        if (StringUtil.isNotEmpty(loginUserVo))
        {
            BackUser backUser = backUserService.getBackUserByUserId(userId);
            // 修改loginUser值
            loginUserVo.setUser(backUser);
            // 重新设置
            long expire = redisCache.getExpire(redisKey);
            redisCache.setCacheObject(redisKey, loginUserVo, Integer.valueOf(expire + ""), TimeUnit.SECONDS);
        }
    }


}