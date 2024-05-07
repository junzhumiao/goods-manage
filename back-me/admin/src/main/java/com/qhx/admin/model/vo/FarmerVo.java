package com.qhx.admin.model.vo;

import com.qhx.admin.domain.BackUser;
import lombok.Data;

/**
 * @author: jzm
 * @date: 2024-03-31 18:08
 **/

@Data
public class FarmerVo extends BackUser
{
    private boolean isShop; // 是否开通商家,0未开通,1开通
}

