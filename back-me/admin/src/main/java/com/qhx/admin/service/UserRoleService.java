package com.qhx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.UserRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-03
 */
public interface UserRoleService extends IService<UserRole> {

    boolean isAdmin(Long userId);

    boolean createUserRole(Long userId, Integer role_farmer);
}
