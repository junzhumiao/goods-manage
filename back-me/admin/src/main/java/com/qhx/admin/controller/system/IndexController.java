package com.qhx.admin.controller.system;

import com.qhx.admin.domain.Category;
import com.qhx.admin.domain.FrontUser;
import com.qhx.admin.model.vo.index.IndexCountVo;
import com.qhx.admin.model.vo.index.main.CategoryMain;
import com.qhx.admin.model.vo.index.main.IndexMainVo;
import com.qhx.admin.model.vo.index.main.UserMain;
import com.qhx.admin.service.*;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.util.DateUtil;
import com.qhx.common.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页控制器(对标后台首页)
 * 求实,由于api划分引入好几个api接口比较抽象,所以单独封装
 *
 * @author: jzm
 * @date: 2024-03-21 15:31
 **/


@RestController
@RequestMapping(path = "/index")
public class IndexController extends BaseController
{

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FrontUserService frontUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping(path = "/count",method = RequestMethod.GET)
    @ApiOperation("首页头部数量(商品数、用户数、订单数、商家数)")
    public AjaxResult indexCount(){
        IndexCountVo indexCountVo = new IndexCountVo();
         // 获取商品数
         indexCountVo.setGoodsCount(goodsService.list().size()+"");
         // 获取累计用户数
         indexCountVo.setUserCount(frontUserService.list().size() +"");
         // 商家数
         indexCountVo.setShopCount(shopService.list().size() +"");
         // 订单数
        indexCountVo.setOrderCount(orderService.list().size() +"");
        return toAjax(indexCountVo);
    }


    @RequestMapping(path = "/main",method = RequestMethod.GET)
    @ApiOperation("首页主体(用户折线图、产品饼图)")
    public AjaxResult indexMain(){
        IndexMainVo indexMainVo = new IndexMainVo();
        // 用户折线图
        indexMainVo.setUserMain(getUserMain());
        // 分类折线图
        indexMainVo.setCategoryMains(getCategoryMains());
        return toAjax(indexMainVo);
    }

    private List<CategoryMain> getCategoryMains()
    {
        List<Category> categories = categoryService.list();
        List<CategoryMain> categoryMains = new ArrayList<>();
        for (Category category : categories)
        {
            CategoryMain categoryMain = new CategoryMain();
            Integer categoryId = category.getCategoryId();
            int count = goodsCategoryService.getCountByCategoryId(categoryId);

            categoryMain.setName(category.getCategoryName());
            categoryMain.setValue(String.valueOf(count));
            categoryMains.add(categoryMain);
        }
        return categoryMains;
    }


    private UserMain getUserMain(){
        List<FrontUser> users = frontUserService.list();
        List<LocalDateTime> userTimes = users.stream().map(FrontUser::getCreateTime).sorted(DateUtil::compare)
                .collect(Collectors.toList());
        return  handTimeGroup(userTimes);
    }

    /**
     * @param userTimes
     * @return times(2004/2、2004/3)、users(1、2)
     */
    private UserMain handTimeGroup(List<LocalDateTime> userTimes)
    {
        HashSet<String> times = new LinkedHashSet<>(); // 截取前6中
        ArrayList<String> users = new ArrayList<>();

        // 遍历时间,去重times
        for (LocalDateTime userTime : userTimes)
        {
            String yymmm = DateUtil.format(userTime, DateUtil.YYYY__MM);
            times.add(yymmm);
        }
        // 处理time找到前7种,不满足7种
        List<String> newTimes =  handSet(times);
        for (String time : newTimes)
        {
            int user = 0;
            for (LocalDateTime userTime : userTimes)
            {
                String yymmm = DateUtil.format(userTime, DateUtil.YYYY__MM);
                if(StringUtil.equals(yymmm,time))
                {
                    user++;
                }
            }
            users.add(String.valueOf(user));
        }
        return new UserMain(users,newTimes);
    }


    private List<String> handSet(HashSet<String> times)
    {
        ArrayList<String> res = new ArrayList<>();
        int i = 0;
        for (String time : times)
        {
            if(i == 7){
                break;
            }
            res.add(time);
            i++;
        }
        return res;
    }


}
