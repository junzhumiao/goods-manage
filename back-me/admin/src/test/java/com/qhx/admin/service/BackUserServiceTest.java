package com.qhx.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.qhx.admin.domain.BackUser;
import com.qhx.admin.domain.Menu;
import com.qhx.admin.model.to.backUser.BackUserDeleteTo;
import com.qhx.admin.model.vo.MenuVo;
import com.qhx.common.model.AjaxResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: jzm
 * @date: 2024-03-02 10:06
 **/

@SpringBootTest
public class BackUserServiceTest
{
    @Autowired
    private BackUserService backUserService;


    @Test
    void getAllMenu()
    {
        List<Menu> paths = backUserService.getMenuByUserId(1L);
        List<MenuVo> menuVos = BeanUtil.copyToList(paths, MenuVo.class);
        List<MenuVo> rootMenuVos = new ArrayList<>();
        // 找到根菜单
        for (MenuVo menuVo : menuVos)
        {
            if(menuVo.getRootId() == -1)
            {
                rootMenuVos.add(menuVo);
            }
        }
        // 将子菜单放到根菜单
        for (MenuVo rMenuVo : rootMenuVos)
        {
            for (MenuVo menuVo : menuVos)
            {
                if(Objects.equals(rMenuVo.getMenuId(), menuVo.getRootId()))
                {
                    if(rMenuVo.getChildren() == null)
                        rMenuVo.setChildren(new ArrayList<>());
                    rMenuVo.getChildren().add(menuVo);
                }
            }
        }

        System.out.println( rootMenuVos);
    }

    @Test
    void createBackUser()
    {
    }

    @Test
    void deleteBackUser()
    {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        BackUserDeleteTo backUserDeleteTo = new BackUserDeleteTo();
        backUserDeleteTo.setUserIds(ids);
        System.out.println(12);

    }

    @Test
    void getAllBackUser()
    {
    }

    @Test
    void updateBackUser()
    {
    }

    @Test
    void getBackUserByUserId()
    {
    }

    @Test
    void updateFarmerStatus()
    {
    }

    @Test
    void updateBackUserPassword()
    {
    }
}
