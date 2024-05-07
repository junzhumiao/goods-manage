package com.qhx.admin.model.vo;

import com.qhx.admin.domain.Menu;
import lombok.Data;

import java.util.List;

/**
 * 响应菜单Vo
 *
 * @author: jzm
 * @date: 2024-03-10 08:57
 **/

@Data
public class MenuVo extends Menu
{
   private List<MenuVo> children;
}
