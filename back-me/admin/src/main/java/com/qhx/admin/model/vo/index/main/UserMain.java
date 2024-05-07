package com.qhx.admin.model.vo.index.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMain
{
    @ApiModelProperty("用户安创建时间分类数")
    private List<String> users;
    @ApiModelProperty("用户创建时间分类数")
    private List<String> times;
}
