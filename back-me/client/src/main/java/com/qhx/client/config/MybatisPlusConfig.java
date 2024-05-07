package com.qhx.client.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qhx.client.context.LoginUserContext;
import com.qhx.client.domain.FrontUser;
import com.qhx.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
// 插入： 首先可以确定的,mybatis-plus是可以对插入操作可以对设置默认值,(插入为null时自动忽略的。)
// 更新： 目前情况看,更新时,也是只更新非null字段。
public class MybatisPlusConfig implements MetaObjectHandler
{


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        FrontUser user = LoginUserContext.getUser();
        if(StringUtil.isNotEmpty(user)){
            this.strictInsertFill(metaObject, "createId", () -> user.getUserId(), Long.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject,"updateTime",() -> LocalDateTime.now(), LocalDateTime.class);
        FrontUser user = LoginUserContext.getUser();
        if(StringUtil.isNotEmpty(user))
        {
            this.strictUpdateFill(metaObject,"updateId",() -> LoginUserContext.getUser().getUserId(), Long.class);
        }
    }
}