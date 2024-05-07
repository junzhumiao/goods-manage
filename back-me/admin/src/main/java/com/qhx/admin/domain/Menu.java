package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: jzm
 * @date: 2024-03-10 09:00
 **/

@Getter
@Setter
@TableName("ap_menu")
@ApiModel(value = "menu对象", description = "")
public class Menu  implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty("菜单路径")
    private String menuPath;

    @ApiModelProperty("菜单名字")
    private String menuName;

    @ApiModelProperty("菜单图标")
    private String menuIcon;

    @ApiModelProperty("根菜单id")
    private Integer rootId;
}
