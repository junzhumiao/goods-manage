package com.qhx.admin.model.to.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: jzm
 * @date: 2024-03-08 08:13
 **/
@Setter
@Getter
public class CategoryDeleteTo
{
    private List<Integer> categoryIds;
}
