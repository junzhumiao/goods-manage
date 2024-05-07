package com.qhx.admin.util;

import com.qhx.admin.model.to.goods.GoodsTo;
import com.qhx.common.util.NotEmptyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-03-08 14:28
 **/

@SpringBootTest
public class NotEmptyUtilTest
{

    @Test
    public void test(){

        GoodsTo goodsTo = new GoodsTo();
        goodsTo.setGoodsName("qhx");
        String result = NotEmptyUtil.checkEmptyFiled(goodsTo, GoodsTo.class);
        System.out.println(result);
        // 反射对象继承对象所有字段,只不过,我们私有字段,不通过反射拉取不到
    }
}
