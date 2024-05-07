package com.qhx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qhx.admin.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> selectAllGoods(@Param("map") HashMap<String, Object> map);

}
