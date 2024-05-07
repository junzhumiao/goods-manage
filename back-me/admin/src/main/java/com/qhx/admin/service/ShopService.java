package com.qhx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhx.admin.domain.Shop;

/**
 * <p>
 *  后台用户 - 额外数据表(商家表)
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-10
 */
public interface ShopService extends IService<Shop> {
    boolean createShop(Shop shop);

    boolean updateShop(Shop shop);

    Shop getShop(Long userId);

    boolean updateShopAvatar(Shop shop);

}
