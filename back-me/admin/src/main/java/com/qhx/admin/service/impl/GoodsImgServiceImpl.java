package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.GoodsImg;
import com.qhx.admin.mapper.GoodsImgMapper;
import com.qhx.admin.model.to.goods.GoodsImgDeleteTo;
import com.qhx.admin.service.GoodsImgService;
import com.qhx.common.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-10
 */
@Service
public class GoodsImgServiceImpl extends ServiceImpl<GoodsImgMapper, GoodsImg> implements GoodsImgService {

    @Override
    public boolean createGoodImgUrls(List<String> imgUrls,Long goodsId)
    {
      return this.updateGoodsImgUrls(imgUrls,goodsId);
    }


    @Override
    public boolean updateGoodsImgUrls(List<String> imgUrls, Long goodsId)
    {
        LambdaQueryWrapper<GoodsImg> queryWrapper = new LambdaQueryWrapper<GoodsImg>()
                .eq(GoodsImg::getGoodsId, goodsId);
        List<String> oldImgUrls = this.list(queryWrapper).stream()
                .map(GoodsImg::getImgUrl).collect(Collectors.toList());
        // 找出要添加的元素
        List<GoodsImg> addList = new ArrayList<>();
        for (String imgUrl : CommonUtil.findList1List2(imgUrls, oldImgUrls))
        {
            GoodsImg goodsImg = new GoodsImg();
            goodsImg.setImgUrl(imgUrl);
            goodsImg.setGoodsId(goodsId);
            addList.add(goodsImg);
        }
        // 找出要删除的元素,没变那就不删除
        List<String> removeList = CommonUtil.findList1List2(oldImgUrls, imgUrls);
        boolean removeEnd = true;
        if(removeList.size() > 0)
        {
            LambdaQueryWrapper<GoodsImg> deleteWrapper = new LambdaQueryWrapper<GoodsImg>()
                    .in(removeList.size()>0,GoodsImg::getImgUrl,removeList);
           removeEnd =  this.remove(deleteWrapper);
        }
        boolean addEnd = true;
        if(addList.size() > 0){
            addEnd = this.saveBatch(addList);
        }
        return addEnd && removeEnd;
    }

    @Override
    public GoodsImg getGoodsImgByFiled(SFunction<GoodsImg,?> colum, Object val){
        LambdaQueryWrapper<GoodsImg> queryWrapper = new LambdaQueryWrapper<GoodsImg>()
                .eq(colum, val);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<GoodsImg> getAllByGoodsId(Long goodsId)
    {
        LambdaQueryWrapper<GoodsImg> queryWrapper = new LambdaQueryWrapper<GoodsImg>()
                .eq(GoodsImg::getGoodsId, goodsId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean deleteGoodsImg(GoodsImgDeleteTo goodsImg)
    {
        List<Long> imgIds = goodsImg.getImgIds();
        LambdaQueryWrapper<GoodsImg> deleteWrapper = new LambdaQueryWrapper<GoodsImg>()
                .in(GoodsImg::getImgId, imgIds);
        return this.remove(deleteWrapper);
    }

}
