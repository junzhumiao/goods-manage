package com.qhx.admin.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.Goods;
import com.qhx.admin.model.to.goods.GoodsDeleteTo;
import com.qhx.admin.model.to.goods.GoodsQueryTo;
import com.qhx.admin.model.to.goods.GoodsTo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-08
 */
public interface GoodsService extends IService<Goods> {


    Goods getOneGoodById(Long goodsId);

    List<Goods> getAllGoods(GoodsQueryTo goodsQueryTo, Long userId);

    boolean updateGoods(GoodsTo goods);

    Goods getOneGoodsByFiled(SFunction<Goods,?> column, Object val);

    boolean deleteGoods(GoodsDeleteTo goodsDeleteTo);

    boolean updateStatus(Goods goods);

    // 判断未完成订单
    boolean isBindNoDoneOrder(Long goodsId);

    // 校验商品列表能否删除
    boolean isBindNoDoneOrder(List<Long> goodsIds);


}
