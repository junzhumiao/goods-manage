package com.qhx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.Category;
import com.qhx.admin.model.to.category.CategoryDeleteTo;
import com.qhx.admin.model.to.category.CategoryQueryTo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
public interface CategoryService extends IService<Category> {

    List<Category> getAllCategory(CategoryQueryTo categoryQueryTo);

    // 校验类名唯一性
    boolean checkCategoryNameUnique(String categoryName);


    boolean createCategory(Category category);

    boolean deleteCategory(CategoryDeleteTo categoryDeleteTo);

    boolean updateCategory(Category category);


    boolean updateStatus(Category category);





}
