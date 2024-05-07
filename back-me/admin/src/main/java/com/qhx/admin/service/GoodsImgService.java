package com.qhx.admin.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.GoodsImg;
import com.qhx.admin.model.to.goods.GoodsImgDeleteTo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-10
 */
public interface GoodsImgService extends IService<GoodsImg> {


    boolean createGoodImgUrls(List<String> imgUrls,Long goodsId);


    /**
     * 查询图片是否存在,不存在插入新数据
     *
     * @param imgUrls 商品图片url
     * @param goodsId 商品id
     * @return
     */
    boolean updateGoodsImgUrls(List<String> imgUrls, Long goodsId);

    GoodsImg getGoodsImgByFiled(SFunction<GoodsImg,?> colum, Object val);

    List<GoodsImg> getAllByGoodsId(Long goodsId);

    boolean deleteGoodsImg(GoodsImgDeleteTo goodsImg);

}
