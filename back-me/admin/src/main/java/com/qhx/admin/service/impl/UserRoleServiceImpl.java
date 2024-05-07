package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.UserRole;
import com.qhx.admin.mapper.UserRoleMapper;
import com.qhx.admin.service.UserRoleService;
import com.qhx.common.constant.Constant;
import com.qhx.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean isAdmin(Long userId)
    {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,userId);
        UserRole userRole = userRoleMapper.selectOne(userRoleLambdaQueryWrapper);

        return StringUtil.equals(userRole.getRoleId(), Constant.Role_Admin);
    }

    @Override
    public boolean createUserRole(Long userId, Integer roleId)
    {
        UserRole userRole = new UserRole(userId, roleId);
        int end = userRoleMapper.insert(userRole);
        return end > 0;
    }

}
