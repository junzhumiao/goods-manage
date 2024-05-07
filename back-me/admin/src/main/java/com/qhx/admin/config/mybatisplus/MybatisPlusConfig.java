package com.qhx.admin.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qhx.admin.service.LoginService;
import com.qhx.common.util.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

@Slf4j
@Component
@EnableTransactionManagement
// 插入： 首先可以确定的,mybatis-plus是可以对插入操作可以对设置默认值,(插入为null时自动忽略的。)
// 更新： 目前情况看,更新时,也是只更新非null字段。
// 查询: 要求返回数据字段 -> 在类中映射字段都能找到(反正不能多了,也不能少了)
public class MybatisPlusConfig implements MetaObjectHandler
{

    @Autowired
    private LoginService loginService;


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        Object user = SecurityUtil.getPrincipal();
        if(!(user instanceof  String)){
            this.strictInsertFill(metaObject, "createBy", () -> loginService.getUserId(), Long.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject,"updateTime", LocalDateTime::now, LocalDateTime.class);
        Object user = SecurityUtil.getPrincipal();
        if(!(user instanceof  String)){
            this.strictInsertFill(metaObject, "updateBy", () -> loginService.getUserId(), Long.class);
        }
    }
}