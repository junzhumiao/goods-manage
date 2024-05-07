package com.qhx.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qhx.admin.domain.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jzm
 * @date: 2024-03-11 09:00
 **/

@SpringBootTest
public class ShopServiceTest
{
    @Autowired
    private ShopService shopService;

    @Test
    public void update(){
        Shop shop = new Shop();
        shop.setUserId(1L);
        shop.setShopName("qhx");
        LambdaQueryWrapper<Shop> updateWrapper = new LambdaQueryWrapper<Shop>()
                .eq(Shop::getUserId, shop.getUserId());

        shopService.update(shop,updateWrapper);
    }
}
