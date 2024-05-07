package com.qhx.admin.mapper;

import com.qhx.admin.domain.Category;
import com.qhx.admin.domain.GoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@Mapper
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    List<Category> selectAllCategoryByGoodsId(Long goodsId);

}
