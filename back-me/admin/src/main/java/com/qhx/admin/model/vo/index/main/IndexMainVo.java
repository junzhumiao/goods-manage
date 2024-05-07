package com.qhx.admin.model.vo.index.main;

import lombok.Data;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-21 16:31
 **/

@Data
public class IndexMainVo
{
    private UserMain userMain;
    private List<CategoryMain> categoryMains;
}


