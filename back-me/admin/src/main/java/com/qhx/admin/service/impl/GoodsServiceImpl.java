package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.Goods;
import com.qhx.admin.domain.Order;
import com.qhx.admin.mapper.GoodsMapper;
import com.qhx.admin.model.to.goods.GoodsDeleteTo;
import com.qhx.admin.model.to.goods.GoodsQueryTo;
import com.qhx.admin.model.to.goods.GoodsTo;
import com.qhx.admin.service.GoodsService;
import com.qhx.admin.service.OrderService;
import com.qhx.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsMapper goodsMapper;



    @Override
    public Goods getOneGoodById(Long goodsId)
    {
        return getOneGoodsByFiled(Goods::getGoodsId,goodsId);
    }

    @Override
    public List<Goods> getAllGoods(GoodsQueryTo goodsQueryTo,Long userId)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("q",goodsQueryTo);
        map.put("userId",userId);
        return goodsMapper.selectAllGoods(map);
    }

    @Override
    public boolean updateGoods(GoodsTo goods)
    {
        // 原列有空格?
        LambdaQueryWrapper<Goods> updateWrapper = new LambdaQueryWrapper<Goods>()
                .eq(Goods::getGoodsId, goods.getGoodsId());

        return this.update(goods,updateWrapper);
    }

    @Override
    public  Goods getOneGoodsByFiled(SFunction<Goods,?> column, Object val)
    {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<Goods>()
                .eq(column, val);

        return this.getOne(queryWrapper);
    }

    @Override
    public boolean deleteGoods(GoodsDeleteTo goodsDeleteTo)
    {
        List<Long> goodsIds = goodsDeleteTo.getGoodsIds();
        LambdaQueryWrapper<Goods> deleteWrapper = new LambdaQueryWrapper<Goods>()
                .in(Goods::getGoodsId, goodsIds);
        return this.remove(deleteWrapper);
    }

    @Override
    public boolean updateStatus(Goods goods)
    {
        LambdaQueryWrapper<Goods> updateWrapper = new LambdaQueryWrapper<Goods>()
                .eq(Goods::getGoodsId,goods.getGoodsId());

        return this.update(goods,updateWrapper);
    }


    @Override
    public boolean isBindNoDoneOrder(Long goodsId){
        List<Order> list =  orderService.getNoDoneOrder(goodsId);
        if(StringUtil.isNotEmpty(list) && list.size()  > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isBindNoDoneOrder(List<Long> goodsIds){
        for (Long goodsId : goodsIds)
        {
            if(isBindNoDoneOrder(goodsId)){
                return true;
            }
        }
        return false;
    }

}
