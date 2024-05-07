package com.qhx.common.aop;

import com.qhx.common.annotation.DataSource;
import com.qhx.common.config.datasource.DynamicDataSource;
import com.qhx.common.enums.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 *
 * @author jzm
 * @version V1.0.0
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.qhx.common.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        // 处理方法
        Method method = signature.getMethod();
        // 处理类
        Class<?> cls = method.getDeclaringClass();
        DataSource clsDs = cls.getAnnotation(DataSource.class);

        DataSource meDs = method.getAnnotation(DataSource.class);

        // 处理设置数据源
        if (meDs == null ) {
            if(clsDs == null){
                log.debug("set datasource is " + DataSourceType.MASTER);
            }else{
                DynamicDataSource.setDataSource(clsDs.value().name());
                log.debug("set datasource is" + clsDs.value().name());
            }
        } else {
            DynamicDataSource.setDataSource(meDs.value().name());
            log.debug("set datasource is " + meDs.value().name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            log.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

