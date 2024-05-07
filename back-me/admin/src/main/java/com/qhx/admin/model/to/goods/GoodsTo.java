package com.qhx.admin.model.to.goods;

import com.qhx.admin.domain.Goods;
import lombok.Data;

import java.util.List;

/**
 * 商品to
 *
 * @author: jzm
 * @date: 2024-03-07 22:15
 **/

@Data
public class GoodsTo extends Goods
{
    // 图片Url的合法性
    private List<String> imgUrls;

    // 商品分类id
    private List<Integer> categoryIds;
}
