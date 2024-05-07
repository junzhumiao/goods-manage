package com.qhx.admin.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.qhx.admin.domain.FrontUser;
import com.qhx.admin.domain.Goods;
import com.qhx.admin.domain.Order;
import com.qhx.admin.domain.Shop;
import com.qhx.admin.model.to.order.OrderDeleteTo;
import com.qhx.admin.model.to.order.OrderQueryTo;
import com.qhx.admin.model.vo.OrderVo;
import com.qhx.admin.service.*;
import com.qhx.common.constant.Constant;
import com.qhx.common.controller.BaseController;
import com.qhx.common.model.AjaxResult;
import com.qhx.common.model.PageResult;
import com.qhx.common.util.NotEmptyUtil;
import com.qhx.common.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController
{

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private FrontUserService frontUserService;

    @Autowired
    private ShopService shopService;



    @RequestMapping(path = "/get/all",method = RequestMethod.POST)
    @ApiOperation("获取所有订单记录")
    public PageResult getAll(@RequestBody OrderQueryTo orderQueryTo){
        Integer page = orderQueryTo.getPage();
        Integer pageSize = orderQueryTo.getPageSize();
        List<OrderVo> orderVos = startOrderPage(page, pageSize, OrderVo.class, () ->
        {
            Long shopId = null;
            if(!loginService.isAdmin()){
                shopId = loginService.getUserId();
            }
            orderService.getAllOrder(orderQueryTo,shopId);
        });
        for (OrderVo orderVo : orderVos)
        {
            Long goodsId = orderVo.getGoodsId();
            Goods goods = goodsService.getOneGoodById(goodsId);
            orderVo.setGoodsName(goods.getGoodsName());
            Long shopId = orderVo.getShopId();
            Shop shop = shopService.getShop(shopId);
            orderVo.setShopAvatar(shop.getShopAvatar());
            orderVo.setShopName(shop.getShopName());
        }

        return success(orderVos,orderVos.size());
    }


    @RequestMapping(path = "/get/one",method = RequestMethod.GET)
    @ApiOperation("根据订单id查询订单详细信息")
    public AjaxResult getOneByOrderId(Long orderId){
        OrderVo order =  orderService.getOneByOrderId(orderId);
        if(StringUtil.isEmpty(order))
        {
            return toAjax(order);
        }
        Shop shop = shopService.getShop(order.getShopId());
        BeanUtil.copyProperties(shop,order,false);
        return toAjax(order);
    }

    @RequestMapping(path = "/create",method = RequestMethod.POST)
    @ApiOperation("创建订单")

    public AjaxResult create(@RequestBody Order order){
        String errPre = "创建订单错误：";
        if(!loginService.isAdmin()){ // 校验管理员
            order.setShopId(order.getShopId());
        }

        String errMes = NotEmptyUtil.checkEmptyFiled(order); // 校验必填字段用户输入的有效性
        if(StringUtil.isNotEmpty(errMes)){
           return error( errPre + errMes);
        }
        // 校验goodsId、shopId、buyerId的有效性。
        FrontUser frontUser = frontUserService.getOneByUserId(order.getBuyerId());
        if(StringUtil.isNotEmpty(frontUser))
        {
            return error(errPre + "用户编号查询不存在,请检查用户编号有效性!");
        }
        Shop shop = shopService.getShop(order.getShopId());
        if(StringUtil.isEmpty(shop))
        {
            return error(errPre + "商家编号查询不存在!");
        }
        Goods goods = goodsService.getOneGoodById(order.getGoodsId());
        if(StringUtil.isEmpty(goods))
        {
            return error(errPre + "农产品编号不存在!");
        }
        // 查询农户下面是否有这个农产品
        Goods goodsOne = goodsService.getOneGoodsByFiled(Goods::getShopId, order.getShopId());
        if(StringUtil.isEmpty(goodsOne))
        {
            return error(errMes + "该商家编号下面并没有该农产品!");
        }
        // 这个uuid证明唯一性(唯一农产品编码)
        boolean end = false;
        String uuid = "";
        while (!end){
            uuid = IdUtil.simpleUUID();
            order.setOrderNum(uuid);
            try
            {
                end =  orderService.save(order);
            }catch (DuplicateKeyException e){ //  产品唯一键重复
                end = false;
            }
        }
        return toAjax(end);
    }



    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ApiOperation("修改订单")
    public AjaxResult update(@RequestBody Order order){
        String errPre = "修改订单错误：";
        if(StringUtil.isEmpty(order.getExpressName())){
            return error(errPre +"输入快递名称为空！");
        }else if(StringUtil.isEmpty(order.getExpressNum())){
            return error(errPre+"输入快递单号为空！");
        }
        if(StringUtil.isEmpty(order.getSenderAddress()) || StringUtil.isEmpty(order.getSenderName())
                || StringUtil.isEmpty(order.getSenderPhone()))
        {
            return error(errPre+"输入发送人相关信息不能为空!");
        }
        boolean end =  orderService.updateOrder(order);
        return toAjax(end);
    }


    @RequestMapping(path = "/update/status",method = RequestMethod.POST)
    @ApiOperation("修改订单状态")
    public AjaxResult updateStatus(@RequestBody Order order){
        if(!checkStatus(order.getStatus()))
        {
            return error("订单状态字符不合法!");
        }

        boolean end =  orderService.updateStatus(order);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/refund",method = RequestMethod.POST)
    @ApiOperation("修改订单是否退款")
    public AjaxResult updateRefund(@RequestBody Order order){
       boolean end = orderService.updateRefund(order);
        return toAjax(end);
    }

    @RequestMapping(path = "/update/cancel",method = RequestMethod.POST)
    @ApiOperation("修改订单是否取消")
    public AjaxResult updateCancel(@RequestBody Order order){
        boolean end = orderService.updateCancel(order);
        return toAjax(end);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation("删除订单")
    public AjaxResult deleteOrder(@RequestBody OrderDeleteTo orderDeleteTo){
        if(!orderService.isCanDeleteOrder(orderDeleteTo)){
            return error("订单中有未完成/未取消的。");
        }
        boolean end =  orderService.deleteOrder(orderDeleteTo);
        return toAjax(end);
    }

    private boolean checkStatus(String status)
    {
        if(StringUtil.equalsAny(status,Constant.Order_UnPaid,Constant.Order_UnShipped
                ,Constant.Order_Shipped,Constant.Order_Done))
        {
            return true;
        }
        return false;
    }



}
