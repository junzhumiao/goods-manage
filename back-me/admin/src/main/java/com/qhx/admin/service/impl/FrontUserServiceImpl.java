package com.qhx.admin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.FrontUser;
import com.qhx.admin.mapper.FrontUserMapper;
import com.qhx.admin.model.to.frontUser.FrontUserDeleteTo;
import com.qhx.admin.model.to.frontUser.FrontUserQueryTo;
import com.qhx.admin.service.BackUserService;
import com.qhx.admin.service.FrontUserService;
import com.qhx.common.model.to.user.PassTo;
import com.qhx.common.model.to.user.PasswordNotOldTo;
import com.qhx.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements FrontUserService {
    
    @Autowired
    private FrontUserMapper frontUserMapper;

    @Autowired
    private BackUserService backUserService;

    @Override
    public FrontUser getOneByUserId(Long userId)
    {
        return getFrontUserByField(FrontUser::getUserId,userId);
    }

    @Override
    public List<FrontUser> getAllFrontUser(FrontUserQueryTo frontUserQueryTo)
    {
        String username = frontUserQueryTo.getUsername();
        String phone = frontUserQueryTo.getPhone();
        String status = frontUserQueryTo.getStatus();
        LocalDateTime beginTime = frontUserQueryTo.getBeginTime();
        LocalDateTime endTime = frontUserQueryTo.getEndTime();

        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<FrontUser>()
        .like(StringUtil.isNotEmpty(username),FrontUser::getUsername,username)
        .eq(StringUtil.isNotEmpty(phone),FrontUser::getPhone,phone)
               .eq(StringUtil.isNotEmpty(status),FrontUser::getStatus,status)
                .ge(StringUtil.isNotEmpty(endTime),FrontUser::getCreateTime,beginTime)
                .le(StringUtil.isNotEmpty(beginTime),FrontUser::getCreateTime, endTime);

        List<FrontUser> userList = frontUserMapper.selectList(queryWrapper);
        return userList;
    }

    @Override
    public boolean createFrontUser(FrontUser frontUser)
    {
        String username = frontUser.getUsername();
        String nickname = frontUser.getNickname();
        String phone = frontUser.getPhone();
        String password = frontUser.getPassword();
        phone = phone.substring(phone.length() - 4);
        if(StringUtil.isEmpty(username))
        {
            frontUser.setUsername("用户:"+ phone);
        }
        if(StringUtil.isEmpty(nickname))
        {
            frontUser.setNickname("用户:"+ phone);
        }
        password = MD5.create().digestHex(password);
        frontUser.setPassword(password);
        return this.save(frontUser);
    }
    

    @Override
    public boolean updatePassword(PassTo passTo)
    {
        String oldPassword = passTo.getOldPassword();
        PasswordNotOldTo to = new PasswordNotOldTo();
        to.setPassword(MD5.create().digestHex(oldPassword));
        to.setNewPassword(passTo.getNewPassword());
        return this.updateMD5Pass(to);
    }

    @Override
    public boolean updateStatus(FrontUser frontUser)
    {
        FrontUser user = new FrontUser();
        user.setStatus(frontUser.getStatus());

        LambdaQueryWrapper<FrontUser> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(FrontUser::getUserId,frontUser.getUserId());
        
        return this.update(user,updateWrapper);
    }


    @Override
    public boolean updateFrontUser(FrontUser frontUser)
    {
        Long userId = frontUser.getUserId();
        LambdaQueryWrapper<FrontUser> updateWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(StringUtil.isNotEmpty(userId),FrontUser::getUserId,userId);
        return this.update(frontUser,updateWrapper);
    }

    @Override
    public boolean deleteFrontUser(FrontUserDeleteTo frontUserDeleteTo)
    {
        List<Long> userIds = frontUserDeleteTo.getUserIds();
        LambdaQueryWrapper<FrontUser> deleteWrapper = new LambdaQueryWrapper<FrontUser>()
                .in(FrontUser::getUserId, userIds);
        return this.remove(deleteWrapper);
    }

    @Override
    public boolean checkPasswordUnique(String password)
    {
        password = MD5.create().digestHex(password);
        return checkMD5PassUnique(password);
    }

    @Override
    public boolean checkMD5PassUnique(String password)
    {
        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(FrontUser::getPassword, password);
        FrontUser user = frontUserMapper.selectOne(queryWrapper);

        return StringUtil.isEmpty(user);
    }

    @Override
    public boolean isPasswordExists(String password)
    {
        password = MD5.create().digestHex(password);
        return !checkPasswordUnique(password);
    }
    
    @Override
    public boolean isMD5PassExists(String password)
    {
        return !checkMD5PassUnique(password);
    }

    @Override
    public boolean checkPhoneUnique(FrontUser frontUser)
    {
        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(FrontUser::getPhone, frontUser.getPhone());
        FrontUser user = frontUserMapper.selectOne(queryWrapper);
        return StringUtil.isEmpty(user);
    }

    @Override
    public boolean checkUpPhoneUnique(FrontUser frontUser)
    {
        FrontUser user = getFrontUserByField(FrontUser::getPhone, frontUser.getPhone());
        if(StringUtil.isEmpty(user) || Objects.equals(frontUser.getUserId(), user.getUserId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkEmailUnique(FrontUser frontUser)
    {
        FrontUser user = getFrontUserByField(FrontUser::getEmail, frontUser.getEmail());
        return StringUtil.isEmpty(user);
    }

    @Override
    public boolean checkUpEmailUnique(FrontUser frontUser)
    {
        FrontUser user = getFrontUserByField(FrontUser::getEmail, frontUser.getEmail());
        if( StringUtil.isEmpty(user) || Objects.equals(user.getUserId(), frontUser.getUserId()))
        {
            return true;
        }
        return false;
    }


    @Override
    public boolean updateMD5Pass(PasswordNotOldTo passwordNotOldTo)
    {
        // 加密
        String newPassword = passwordNotOldTo.getNewPassword();
        newPassword = MD5.create().digestHex(newPassword);
        
        String password = passwordNotOldTo.getPassword();
        LambdaQueryWrapper<FrontUser> updateWrapper = new LambdaQueryWrapper<FrontUser>()
                .eq(FrontUser::getPassword, password);

        FrontUser user = new FrontUser();
        user.setPassword(newPassword);
        user.setPwdUpdateDate(LocalDateTime.now());
        return this.update(user,updateWrapper);
    }

    @Override
    public FrontUser getFrontUserByField(SFunction<FrontUser, ?> colum, Object val)
    {
        LambdaQueryWrapper<FrontUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(colum,val);
        FrontUser user = frontUserMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public BackUser isBindBackUser(String md5Pass)
    {
        return backUserService.getBackUserByField(BackUser::getPassword,md5Pass);
    }


}
