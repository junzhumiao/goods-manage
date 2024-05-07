package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.Category;
import com.qhx.admin.domain.GoodsCategory;
import com.qhx.admin.mapper.GoodsCategoryMapper;
import com.qhx.admin.service.GoodsCategoryService;
import com.qhx.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public List<GoodsCategory> geAllByCategoryId(List<Integer> categoryIds)
    {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<GoodsCategory>()
                .in(GoodsCategory::getCategoryId,categoryIds);
        List<GoodsCategory> goodsCategoryList = this.list(queryWrapper);
        return goodsCategoryList;
    }

    @Override
    public Integer getCountByCategoryId(List<Integer> categoryIds)
    {
        List<GoodsCategory> goodsCategories = geAllByCategoryId(categoryIds);
        return goodsCategories == null ? 0 : goodsCategories.size();
    }

    @Override
    public boolean createGoodsCategory(List<Integer> categoryIds, Long goodsId)
    {
        ArrayList<GoodsCategory> goodsCategoryIds = new ArrayList<>();
        for (Integer categoryId : categoryIds)
        {
            GoodsCategory goodsCategory = new GoodsCategory(goodsId,categoryId);
            goodsCategoryIds.add(goodsCategory);
        }
        return this.saveBatch(goodsCategoryIds);
    }


    @Override
    public boolean updateGoodsCategory(List<Integer> categoryIds, Long goodsId)
    {
        // 查询出原先的分类id列表
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<GoodsCategory>()
                .eq(GoodsCategory::getGoodsId, goodsId);

        List<GoodsCategory> goodsCategoryList = this.list(queryWrapper);
        List<Integer> oldCategoryIds = goodsCategoryList.stream()
                .map(GoodsCategory::getCategoryId).collect(Collectors.toList());
       // 找出新分类里面不同id,可以确定是需要新增
        List<GoodsCategory> addCategoryIds = new ArrayList<>();
        for (Integer addId: CommonUtil.findList1List2(categoryIds, oldCategoryIds))
        {
            addCategoryIds.add(new GoodsCategory(goodsId,addId));
        }

        // 找出老分类里面不同的id，可以确定是减少的
        List<Integer> removeCategoryIds = CommonUtil.findList1List2(oldCategoryIds,categoryIds);

        // 执行删除和添加操作
        boolean addEnd = true;
        boolean removeEnd = true;

        if(addCategoryIds.size() >0 ){
            addEnd = this.saveBatch(addCategoryIds);
        }
        if(removeCategoryIds.size() >0){
            LambdaQueryWrapper<GoodsCategory> deleteWrapper = new LambdaQueryWrapper<GoodsCategory>()
                    .in(GoodsCategory::getCategoryId, removeCategoryIds);
           removeEnd = this.remove(deleteWrapper);
        }
        return addEnd && removeEnd;
    }

    @Override
    public int getCountByCategoryId(Integer categoryId)
    {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<GoodsCategory>()
                .eq(GoodsCategory::getCategoryId,categoryId);
        return this.list(queryWrapper).size();
    }

    @Override
    public List<Category> getAllCategoryByGoodsId(Long goodsId)
    {
        return goodsCategoryMapper.selectAllCategoryByGoodsId(goodsId);
    }


}
