package com.qhx.admin.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.qhx.admin.domain.*;
import com.qhx.admin.model.to.goods.GoodsDeleteTo;
import com.qhx.admin.model.to.goods.GoodsImgDeleteTo;
import com.qhx.admin.model.to.goods.GoodsQueryTo;
import com.qhx.admin.model.to.goods.GoodsTo;
import com.qhx.admin.model.vo.CategoryVo;
import com.qhx.admin.model.vo.GoodsImgVo;
import com.qhx.admin.model.vo.GoodsVo;
import com.qhx.admin.service.*;
import com.qhx.common.constant.Constant;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.util.NotEmptyUtil;
import com.qhx.common.util.StringUtil;
import com.qhx.common.util.user.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  农产品控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController
{
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsImgService goodsImgService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private BackUserService backUserService;



    @RequestMapping(path = "/get/all",method = RequestMethod.POST)
    @ApiOperation(value = "获取全部农产品信息",notes = "管理员可查询全部商品,农户查询属于自己部分的商品")
    // 分页插件分页最近一次sql查询数据(服了)
    public PageResult getAll(@RequestBody  GoodsQueryTo goodsQueryTo){
        // 分页查询goods
        Integer page = goodsQueryTo.getPage();
        Integer pageSize = goodsQueryTo.getPageSize();
        List<GoodsVo> goodsVos = startOrderPage(page, pageSize, GoodsVo.class, () ->
        {
            Long userId = null;
            Long shopId = goodsQueryTo.getShopId();
            if(StringUtil.isNotEmpty(shopId)) // 管理员
                userId = shopId;
            if (!loginService.isAdmin()) // 农户
                userId = loginService.getUserId();

            goodsService.getAllGoods(goodsQueryTo, userId);
        });

        // 查询goods所属category,封账goodsVo
        long total = goodsVos.size();
        for (GoodsVo goodsVo : goodsVos)
        {
            Long goodsId = goodsVo.getGoodsId();
            // 获取goodsId下面所有的分类
            List<Category> categories =  goodsCategoryService.getAllCategoryByGoodsId(goodsId);
            List<CategoryVo> categoryVos = BeanUtil.copyToList(categories, CategoryVo.class);
            goodsVo.setCategoryVos(categoryVos);
            // 获取goodsId对应的所有商品图片
            List<GoodsImg> goodsImgs =  goodsImgService.getAllByGoodsId(goodsId);
            List<GoodsImgVo> goodsImgVos = BeanUtil.copyToList(goodsImgs, GoodsImgVo.class);
            goodsVo.setGoodsImgVos(goodsImgVos);
        }
        return success(goodsVos,total);
    }




    @RequestMapping(path = "/create",method = RequestMethod.POST)
    @ApiOperation(value = "添加农产品",notes = "分管理员和农户添加,管理员添加需输入shop_id") // 创建商品的时候,加上该用户是否开通商家
    public AjaxResult create(@RequestBody GoodsTo goodsTo){
        String errPre = "新增农产品失败：";
        // 管理员决定是否设置shop_id
        if(!loginService.isAdmin()){
            goodsTo.setShopId(loginService.getUserId());
        }
        // 校验对应的shopId是否注册商家
        String errMes = checkShopId(goodsTo.getShopId());
        if(StringUtil.isNotEmpty(errMes))
        {
            return error(errPre + errMes);
        }

        // 商品字段非空校验
        String result = NotEmptyUtil.checkEmptyFiled(goodsTo);
        if(StringUtil.isNotEmpty(result))
        {
            return error(errPre+result);
        }
        // 这个uuid证明唯一性(唯一农产品编码)
        boolean end = false;
        String uuid = "";
        while (!end){
            uuid = IdUtil.simpleUUID();
            goodsTo.setGoodsNum(uuid);
           try
           {
               end =  goodsService.save(goodsTo);
           }catch (DuplicateKeyException e){ // 唯一键重复
               end = false;
           }
        }
        Goods goods =  goodsService.getOneGoodsByFiled(Goods::getGoodsNum,uuid);
        // 校验商品图片
        List<String> imgUrls = goodsTo.getImgUrls();
        String imgResult = UserUtil.checkImgUrls(imgUrls);
        if(StringUtil.isNotEmpty(imgResult))
        {
            return error(imgResult);
        }

        // 插入商品图片,这里直接忽略了,产品图片可以默认值
         if(imgUrls.size() > 0){
             goodsImgService.createGoodImgUrls(imgUrls,goods.getGoodsId());
         }
        // 插入农产品分类
        if(goodsTo.getCategoryIds().size() > 0){
            goodsCategoryService.createGoodsCategory(goodsTo.getCategoryIds(),goods.getGoodsId());
        }
        return toAjax(end);
    }

    private String checkShopId(Long shopId)
    {
        if(StringUtil.isEmpty(shopId)){
            return "农户并未开通商家服务!";
        }else{
            if(StringUtil.isEmpty(shopService.getShop(shopId))){
                return "农户并未开通商家服务!";
            }
            return null;
        }
    }


    @RequestMapping(path = "/delete/goods/img",method = RequestMethod.POST)
    @ApiOperation(value = "删除产品图片")
    public AjaxResult deleteGoodsImg(@RequestBody GoodsImgDeleteTo goodsImgDeleteTo){
            boolean end =  goodsImgService.deleteGoodsImg(goodsImgDeleteTo);
            return toAjax(end);
    }


    @RequestMapping(path = "/update/goods/img",method = RequestMethod.POST)
    @ApiOperation(value = "修改产品图片")
    public AjaxResult updateGoodsImg(@RequestBody GoodsTo goodsTo){
        List<String> imgUrls = goodsTo.getImgUrls();
        String errMes = UserUtil.checkImgUrls(imgUrls);
        if(StringUtil.isNotEmpty(errMes))
        {
            return error(errMes);
        }
        boolean end = goodsImgService.updateGoodsImgUrls(imgUrls, goodsTo.getShopId());
        return toAjax(end);
    }




    @RequestMapping(path = "/get/one",method = RequestMethod.GET)
    @ApiOperation("获取农产品详细信息")
    public AjaxResult getOneById(Long  goodsId){
         Goods goods =  goodsService.getOneGoodById(goodsId);
        return toAjax(goods);
    }

    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改农产品信息")
    @Transactional
    public AjaxResult update(@RequestBody GoodsTo goodsTo){
        String errPre = "修改农产品失败:";
        // 修改产品图片
        List<String> imgUrls = goodsTo.getImgUrls();
        String giEnd = UserUtil.checkImgUrls(imgUrls);
        if(StringUtil.isNotEmpty(giEnd)){
            return error(errPre+giEnd);
        }
        boolean ugiEnd =  goodsImgService.updateGoodsImgUrls(imgUrls,goodsTo.getGoodsId());
        if(!ugiEnd){
          return error(errPre+"修改产品图片失败!");
        }
        // 修改商品分类
        boolean uGcEnd = goodsCategoryService.updateGoodsCategory(goodsTo.getCategoryIds(),goodsTo.getGoodsId());
        if(!uGcEnd){
            return error(errPre+"修改商品分类失败!");
        }
        boolean end =  goodsService.updateGoods(goodsTo);
        return toAjax(end && uGcEnd);
    }


    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation("删除农产品")
    public AjaxResult delete(@RequestBody GoodsDeleteTo goodsDeleteTo){
        loginService.checkAdmin();
        if(goodsService.isBindNoDoneOrder(goodsDeleteTo.getGoodsIds()))
        {
            return error("商品不能删除!有订单未完成!");
        }
        boolean end =  goodsService.deleteGoods(goodsDeleteTo);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/status",method = RequestMethod.POST)
    @ApiOperation("修改农产品状态")
    public AjaxResult updateStatus(@RequestBody Goods goods){
        loginService.checkAdmin();
        if(goodsService.isBindNoDoneOrder(goods.getGoodsId())
                && StringUtil.equals(goods.getStatus(),Constant.Disable))
        {
            return error("商品不能禁用!有订单未完成！");
        }
        boolean end =  goodsService.updateStatus(goods);
        return toAjax(end);
    }


}
