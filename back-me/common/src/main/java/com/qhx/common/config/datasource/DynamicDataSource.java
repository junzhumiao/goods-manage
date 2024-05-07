package com.qhx.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author jzm
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    /**
     * 获取当前线程正在用的数据源
     */
    @Override
    protected Object determineCurrentLookupKey()
    {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }

    public static void setDataSource(String dataSource){
        DynamicDataSourceContextHolder.setDataSourceType(dataSource);
    }

    public static void clearDataSource(){
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}