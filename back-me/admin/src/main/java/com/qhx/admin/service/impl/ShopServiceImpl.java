package com.qhx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhx.admin.domain.Shop;
import com.qhx.admin.mapper.ShopMapper;
import com.qhx.admin.service.ShopService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-10
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Override
    public boolean createShop(Shop shop)
    {
        return this.save(shop);
    }

    @Override
    public boolean updateShop(Shop shop)
    {
        LambdaQueryWrapper<Shop> updateWrapper = new LambdaQueryWrapper<Shop>()
                .eq(Shop::getUserId, shop.getUserId());
        return this.update(shop,updateWrapper);
    }

    @Override
    public Shop getShop(Long userId)
    {
        LambdaQueryWrapper<Shop> queryWrapper = new LambdaQueryWrapper<Shop>()
                .eq(Shop::getUserId, userId);

        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateShopAvatar(Shop shop)
    {
        LambdaQueryWrapper<Shop> updateWrapper = new LambdaQueryWrapper<Shop>()
                .eq(Shop::getUserId, shop.getUserId());

        Shop newShop = new Shop();
        newShop.setShopAvatar(shop.getShopAvatar());

        return this.update(newShop,updateWrapper);
    }
}
