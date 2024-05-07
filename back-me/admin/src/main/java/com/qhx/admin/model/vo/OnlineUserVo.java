package com.qhx.admin.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: jzm
 * @date: 2024-03-28 16:53
 **/

@Data
public class OnlineUserVo
{
    private Long userId;

    private String username;
    private String loginIp;
    private LocalDateTime loginDate;

    private String tokenId;
    private String loginLocation;
    private String browserName;
    private String osName;
}
