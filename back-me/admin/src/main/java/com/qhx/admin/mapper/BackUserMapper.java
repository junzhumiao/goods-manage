package com.qhx.admin.mapper;

import com.qhx.admin.domain.BackUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qhx.admin.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author qhx2004
 * @since 2024-02-27
 */
@Mapper
public interface BackUserMapper extends BaseMapper<BackUser> {

    List<Menu> selectMenuByUserId(Long userId);

    List<BackUser> selectAllUser(@Param("map") HashMap<String, Object> map);
}
