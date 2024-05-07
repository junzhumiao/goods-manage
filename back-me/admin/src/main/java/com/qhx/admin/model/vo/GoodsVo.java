package com.qhx.admin.model.vo;

import com.qhx.admin.domain.Goods;
import lombok.Data;

import java.util.List;

/**
 * goods响应类
 *
 * @author: jzm
 * @date: 2024-03-26 14:44
 **/

@Data
public class GoodsVo extends Goods
{
    private List<CategoryVo> categoryVos;

    private List<GoodsImgVo> goodsImgVos;
}
