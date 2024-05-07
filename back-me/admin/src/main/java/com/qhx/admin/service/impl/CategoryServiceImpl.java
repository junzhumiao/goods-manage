package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.Category;
import com.qhx.admin.mapper.CategoryMapper;
import com.qhx.admin.model.to.category.CategoryDeleteTo;
import com.qhx.admin.model.to.category.CategoryQueryTo;
import com.qhx.admin.service.CategoryService;
import com.qhx.common.util.StringUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> getAllCategory(CategoryQueryTo categoryQueryTo)
    {
        String categoryName = categoryQueryTo.getCategoryName();
        LocalDateTime endTime = categoryQueryTo.getEndTime();
        LocalDateTime beginTime = categoryQueryTo.getBeginTime();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>()
                .like(StringUtil.isNotEmpty(categoryName),Category::getCategoryName,categoryName)
                 .ge(StringUtil.isNotEmpty(endTime), Category::getCreateTime, beginTime)
                .le(StringUtil.isNotEmpty(beginTime), Category::getCreateTime, endTime);

        List<Category> categoryList = this.list(queryWrapper);
        return categoryList;
    }

    @Override
    public boolean checkCategoryNameUnique(String categoryName)
    {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName,categoryName);
        Category category = this.getOne(queryWrapper);
        if(category != null){
            return false;
        }
        return true;
    }


    @Override
    public boolean createCategory(Category category)
    {
        Category newCategory = new Category();
        newCategory.setCategoryName(category.getCategoryName());
        return this.save(newCategory);
    }

    @Override
    public boolean deleteCategory(CategoryDeleteTo categoryDeleteTo)
    {
        List<Integer> categoryIds = categoryDeleteTo.getCategoryIds();
        LambdaQueryWrapper<Category> deleteWrapper =
                new LambdaQueryWrapper<Category>().in(Category::getCategoryId, categoryIds);

        return this.remove(deleteWrapper);
    }

    @Override
    public boolean updateCategory(Category category)
    {
        String categoryName = category.getCategoryName();
        LambdaQueryWrapper<Category> updateWrapper =
                new LambdaQueryWrapper<Category>().eq(Category::getCategoryId, category.getCategoryId());
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryName);

        return this.update(newCategory,updateWrapper);
    }


    @Override
    public boolean updateStatus(Category category)
    {
        String status = category.getStatus();
        LambdaQueryWrapper<Category> updateWrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryId,category.getCategoryId());

        Category newCategory = new Category();
        newCategory.setStatus(category.getStatus());
        return  this.update(newCategory,updateWrapper);
    }


}
