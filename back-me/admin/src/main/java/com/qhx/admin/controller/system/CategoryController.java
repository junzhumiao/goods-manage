package com.qhx.admin.controller.system;

import com.github.pagehelper.PageInfo;
import com.qhx.admin.domain.Category;
import com.qhx.admin.model.to.category.CategoryDeleteTo;
import com.qhx.admin.model.to.category.CategoryQueryTo;
import com.qhx.admin.service.CategoryService;
import com.qhx.admin.service.LoginService;
import com.qhx.common.constant.Constant;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  商品分类控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController
{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/get/all",method = RequestMethod.POST)
    @ApiOperation("获取全部分类")
    public PageResult getAll(@RequestBody  CategoryQueryTo categoryQueryTo){
        Integer pageSize = categoryQueryTo.getPageSize();
        Integer page = categoryQueryTo.getPage();
        PageInfo pageInfo = startOrderPage(page, pageSize, () ->
        {
            categoryService.getAllCategory(categoryQueryTo);
        });
        return toAjax(pageInfo);
    }


    @RequestMapping(path = "/create",method = RequestMethod.POST)
    @ApiOperation("添加分类")
    public AjaxResult create(@RequestBody Category category){
        loginService.checkAdmin();
        if(!checkCategoryNameFormat(category.getCategoryName())){
           return error("产品分类名要求1-5位长度字符串！");
        }
        if(!categoryService.checkCategoryNameUnique(category.getCategoryName()))
        {
            return error("产品分类名已经存在！");
        }
        boolean end =  categoryService.createCategory(category);
        return  toAjax(end);

    }

    private boolean checkCategoryNameFormat(String categoryName){
        int l = categoryName.length();
        if(l >= Constant.CATEGORY_NAME_MIN_LEN && l <= Constant.CATEGORY_NAME_MAX_LEN){

            return true;
        }
        return false;
    }


    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation("删除分类")
    public AjaxResult delete(@RequestBody CategoryDeleteTo categoryDeleteTo){
        loginService.checkAdmin();
        boolean end =  categoryService.deleteCategory(categoryDeleteTo);
        return toAjax(end);
    }

    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改分类")
    public AjaxResult update(@RequestBody Category category){
        loginService.checkAdmin();
        String categoryName = category.getCategoryName();
        if(StringUtil.isEmpty(categoryName) || !checkCategoryNameFormat(categoryName))
        {
            return error("修改产品分类失败："+category.getCategoryName()+"长度非法!");
        }
        boolean end = categoryService.updateCategory(category);
        return toAjax(end);
    }


    @RequestMapping(path = "/update/status",method = RequestMethod.POST)
    @ApiOperation("修改分类状态")
    public AjaxResult updateStatus(@RequestBody Category category){
        //loginService.checkAdmin();
        if(!UserUtil.verifyStatus(category.getStatus()))
        {
            return error("状态字符不合法!");
        }
        boolean end = categoryService.updateStatus(category);
        return toAjax(end);
    }


}
