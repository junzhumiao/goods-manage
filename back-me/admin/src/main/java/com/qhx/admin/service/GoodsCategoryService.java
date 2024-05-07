package com.qhx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.Category;
import com.qhx.admin.domain.GoodsCategory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

    List<GoodsCategory> geAllByCategoryId(List<Integer> categoryIds);
    Integer getCountByCategoryId(List<Integer> categoryIds);

    boolean createGoodsCategory(List<Integer> categoryIds,Long goodsId);

    // 添加不同的,删除没有的
    boolean updateGoodsCategory(List<Integer> categoryIds, Long goodsId);

    int getCountByCategoryId(Integer categoryId);

    List<Category> getAllCategoryByGoodsId(Long goodsId);

}
