package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-01
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("ap_user_role")
@ApiModel(value = "UserRole对象", description = "")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("后台用户id")
    private Long userId;

    @ApiModelProperty("角色id")
    private Integer roleId;
}
