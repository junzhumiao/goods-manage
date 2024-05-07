package com.qhx.admin.model.vo.index.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryMain {
    @ApiModelProperty("分类名")
    private String name; 
    @ApiModelProperty("分类下面产品数量")
    private String value;
}