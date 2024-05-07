package com.qhx.common.util.user;

import com.qhx.common.constant.UserConstant;
import com.qhx.common.domain.User;
import com.qhx.common.enums.UserStatus;
import com.qhx.common.util.OSSUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.ip.AddressUtil;
import com.qhx.common.util.ip.IpUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户工具类(对用户相关参数的校验)
 *
 * @author: jzm
 * @date: 2024-02-28 10:49
 **/

public class UserUtil
{

    public static void updateLoginInfo(User user){
        // 设置登录者的真实ip地址
        String requestIp = IpUtil.getIpAddr();
        String address = AddressUtil.getRealAddressByIP(requestIp);
        user.setLoginIp(address);
        user.setLoginDate(LocalDateTime.now());
    }

    public  static boolean verifyPassword(String password) {
        if (StringUtil.isEmpty(password) ||   password.length() < UserConstant.PASSWORD_MIN_LENGTH || password.length() > UserConstant.PASSWORD_MAX_LENGTH) {
            return false;
        }
        return true;
    }

    public static boolean verifyUsername(String username) {
        if (StringUtil.isEmpty(username) || username.length() <UserConstant.USERNAME_MIN_LENGTH || username.length() > UserConstant.USERNAME_MAX_LENGTH) {
            return false;
        }
        return true;
    }

    public  static  boolean verifyPhone(String phone) {
      return phone.matches(UserConstant.MOBILE_PHONE_NUMBER_PATTERN);
    }

    public static boolean verifyEmail(String email) {
        return email.matches(UserConstant.EMAIL_PATTERN);
    }


    public static boolean isDelete(String flag){
        return UserStatus.DELETED.getCode().equals(flag);
    }

    public static boolean isDisable(String flag){
        return UserStatus.DISABLE.getCode().equals(flag);
    }

    public static boolean verifyStatus(String status){
        return StringUtil.equalsAnyIgnoreCase(status, UserConstant.NORMAL,UserConstant.EXCEPTION);
    }


    public static boolean checkAvatar(String avatar)
    {
        return avatar.contains(OSSUtil.getOSSDomain()); // 必须是阿里云上传的图片地址
    }


    public  static  String checkImgUrls(List<String> imgUrls){
        StringBuilder result = new StringBuilder();
        for (String imgUrl : imgUrls)
        {
            if(!UserUtil.checkAvatar(imgUrl))
            {
                result.append("图片地址:");
                result.append(imgUrl);
                result.append(",");
            }
        }
        if(StringUtil.isNotEmpty(result.toString()))
        {
            result.append("不合法!");
        }
        return result.toString();
    }


}
