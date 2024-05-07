package com.qhx.admin.service;

import com.qhx.admin.controller.system.GoodsController;
import com.qhx.admin.domain.Goods;
import com.qhx.admin.mapper.GoodsMapper;
import com.qhx.admin.model.to.goods.GoodsQueryTo;
import com.qhx.common.model.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-26 14:32
 **/

@SpringBootTest
public class GoodsServiceTest
{
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void testGoodsService(){
        GoodsQueryTo goodsQueryTo = new GoodsQueryTo();
        //goodsQueryTo.setGoodsName("a");
        HashMap<String, Object> map = new HashMap<>();
        map.put("q",goodsQueryTo);
        map.put("userId",null);

        List<Goods> goodsList = goodsMapper.selectAllGoods(map);
        System.out.println(12);
    }

    @Autowired
    private GoodsController goodsController;


    @Test
    public void testGetGoodsVos(){
        GoodsQueryTo goodsQueryTo = new GoodsQueryTo();
        goodsQueryTo.setPage(1);
        goodsQueryTo.setPageSize(10);
        PageResult all = goodsController.getAll(goodsQueryTo);
        System.out.println(12);

        //6e041ec4935948638ffdcd2927698590
    }

    @Test
    public void testCreateGoods(){

        Goods goods = new Goods();
        goods.setGoodsNum("6e041ec4935948638ffdcd2927698590");
       try
       {
           // 夜约束绑定外键?
           int insert = goodsMapper.insert(goods);
       }catch (Exception e){
           System.out.println(e);
       }
        //6e041ec4935948638ffdcd2927698590
    }
}
