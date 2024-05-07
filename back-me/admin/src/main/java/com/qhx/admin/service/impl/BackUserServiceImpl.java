package com.qhx.admin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.Menu;
import com.qhx.admin.mapper.BackUserMapper;
import com.qhx.admin.model.to.LoginTo;
import com.qhx.admin.model.to.backUser.BackUserDeleteTo;
import com.qhx.admin.model.to.backUser.BackUserQueryTo;
import com.qhx.admin.model.vo.LoginUserVo;
import com.qhx.admin.service.BackUserService;
import com.qhx.common.exception.PermissionException;
import com.qhx.common.model.to.user.PassTo;
import com.qhx.common.model.to.user.PasswordNotOldTo;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 后端用户业务类实现
 *
 * @author: jzm
 * @date: 2024-03-01 19:48
 **/


@Slf4j
@Service
public class  BackUserServiceImpl  extends ServiceImpl<BackUserMapper, BackUser> implements BackUserService
{

    @Autowired
    private BackUserMapper backUserMapper;



    @Override
    public BackUser login(LoginTo loginTo)
    {
        String password = loginTo.getPassword();
        String username = loginTo.getUsername();
        // md5加密
        password = MD5.create().digestHex(password);
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUser::getPassword,password);
        queryWrapper.eq(BackUser::getUsername,username);
        BackUser user = this.getOne(queryWrapper);
        return user;
    }
    

    // 密码是否存在
    public boolean isPasswordExists(String password)
    {   
        return !checkPasswordUnique(password);
    }
    
    public boolean isMD5PassExists(String password){
        return !checkMD5PassUnique(password);
    }

    public boolean checkPasswordUnique(String password)
    {
        password = new MD5().digestHex(password);
        return checkMD5PassUnique(password);
    }

    // 校验md5密码
    private boolean checkMD5PassUnique(String password)
    {
        BackUser user = getBackUserByField(BackUser::getPassword, password);
        if(StringUtil.isEmpty(user))
        {
            return true;
        }
        return false;
    }



    public boolean checkPhoneUnique(BackUser backUser)
    {
        String phone = backUser.getPhone();
        // 电话号是否唯一
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUser::getPhone,phone);
        BackUser user = this.getOne(queryWrapper);
        if(user != null){
           return false;
        }
        return true;
    }


    @Override
    public boolean checkUpPhoneUnique(BackUser backUser)
    {
        String phone = backUser.getPhone();
        BackUser user = getBackUserByField(BackUser::getPhone, phone);
        if( StringUtil.isEmpty(user) || Objects.equals(user.getUserId(), backUser.getUserId())){
            return true;
        }
        return false;
    }


    public boolean checkEmailUnique(BackUser backUser)
    {
        String email = backUser.getEmail();
        // 邮箱是否唯一
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUser::getEmail,email);
        BackUser user = this.getOne(queryWrapper);
        if(user != null)
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkUpEmailUnique(BackUser backUser)
    {
        BackUser user = getBackUserByField(BackUser::getEmail, backUser.getEmail());
        if(StringUtil.isEmpty(user) || StringUtil.equals(user.getEmail(),backUser.getEmail()) ){
            return true;
        }
        return false;
    }


    @Override
    public List<Menu> getMenuByUserId(Long userId)
    {
        return backUserMapper.selectMenuByUserId(userId);
    }

    @Override
    public List<BackUser> getAllBackUser(BackUserQueryTo backUserQueryTo, int roleId)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("q",backUserQueryTo);
        map.put("roleId",roleId);
        return   backUserMapper.selectAllUser(map);
    }

    @Override
    public BackUser getBackUserByUserId(long userId)
    {
      return getBackUserByField(BackUser::getUserId,userId);
    }


    @Override
    public boolean createBackUser(BackUser backUser)
    {
        String phone = backUser.getPhone();
        String password = backUser.getPassword();
        phone = phone.substring(phone.length() - 4);

        if(StringUtil.isEmpty(backUser.getUsername()))
        {
            backUser.setUsername("农户:"+ phone);
        }
        if(StringUtil.isEmpty(backUser.getNickname())){
            backUser.setNickname("农户:"+phone);
        }
        password = MD5.create().digestHex(password);
        backUser.setPassword(password);
        return this.save(backUser);
    }


    @Override
    public boolean updateBackUser(BackUser backUser)
    {
        // mybatis-plus对于修改,对于null数据是不修改的,另外一个修改密码单独一个逻辑
        backUser.setPassword(null);
        LambdaQueryWrapper<BackUser> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(BackUser::getUserId,backUser.getUserId());

        return this.update(backUser, updateWrapper);
    }

    @Override
    // 用户个人操作重置密码
    public boolean updatePassword(PassTo passTo)
    {
        PasswordNotOldTo notOldTo = new PasswordNotOldTo();
        String password = MD5.create().digestHex(passTo.getOldPassword());
        notOldTo.setPassword(password);
        notOldTo.setNewPassword(passTo.getNewPassword());
        return updateMD5Pass(notOldTo);
    }
    
    // 管理员操作重置密码
    public boolean updateMD5Pass(PasswordNotOldTo passwordNotOldTo){
        String password = passwordNotOldTo.getPassword(); 
        String newPassword = passwordNotOldTo.getNewPassword();
        newPassword = MD5.create().digestHex(newPassword);

        LambdaQueryWrapper<BackUser> updateWrapper = new LambdaQueryWrapper<BackUser>()
        .eq(BackUser::getPassword,password);

        BackUser user = new BackUser();
        user.setPassword(newPassword);
        user.setPwdUpdateDate(LocalDateTime.now());
        return this.update(user,updateWrapper);
    }
    

    @Override
    public boolean deleteBackUser(BackUserDeleteTo backUserDeleteTo)
    {
        List<Long> userIds = backUserDeleteTo.getUserIds();
        LoginUserVo loginUserVo = SecurityUtil.getPrincipal();
        for (Long userId : userIds)
        {
            if(Objects.equals(loginUserVo.getUser().getUserId(), userId))
            {
                throw new PermissionException("操作用户不能删除自己!");
            }
        }
        LambdaQueryWrapper<BackUser> deleteWrapper = new LambdaQueryWrapper<BackUser>()
                .in(BackUser::getUserId,userIds);
        int end = backUserMapper.delete(deleteWrapper);
        return end > 0;
    }


    @Override
    public boolean updateStatus(BackUser backUser)
    {
        LoginUserVo loginUserVo = SecurityUtil.getPrincipal();
        if(Objects.equals(loginUserVo.getUser().getUserId(), backUser.getUserId()))
        {
            throw new PermissionException("操作者不修改自己的状态");
        }
        // 修改状态
        LambdaQueryWrapper<BackUser> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(BackUser::getUserId,backUser.getUserId());
        return this.update(backUser, updateWrapper);
    }


    @Override
    public BackUser getBackUserByField(SFunction<BackUser, ?> colum, Object val)
    {
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(colum,val);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateAvatar(BackUser backUser)
    {
        LambdaQueryWrapper<BackUser> updateWrapper = new LambdaQueryWrapper<BackUser>()
                .eq(BackUser::getUserId, backUser.getUserId());

        BackUser newUser = new BackUser();
        newUser.setAvatar(backUser.getAvatar());
        return this.update(backUser,updateWrapper);
    }


}
