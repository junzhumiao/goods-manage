package com.qhx.admin.model.to.category;

import com.qhx.admin.domain.Category;
import lombok.Getter;
import lombok.Setter;


/**
 * @author: jzm
 * @date: 2024-03-07 22:21
 **/

@Setter
@Getter
public class CategoryTo extends Category
{
    private String categoryName;
}
