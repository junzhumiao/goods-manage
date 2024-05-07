package com.qhx.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author: jzm
 * @date: 2024-03-31 11:57
 **/

//@Data
//@Component
//@ConfigurationProperties(prefix = "spring.datasource.druid.master")
public class DruidDataSourceProperties
{
    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private int initialSize = 5;

    private int minIdle = 5;

    private int maxActive = 10;

    private int maxWait = 2;

    private int timeBetweenEvictionRunsMillis = 1000 * 60;

    private int minEvictableIdleTimeMillis = 1000 * 60 * 30;

    private String validationQuery;

    private boolean testWhileIdle = false;

    private boolean testOnBorrow = true;

    private boolean testOnReturn = false;

    private boolean poolPreparedStatements = false;

    private int maxPoolPreparedStatementPerConnectionSize = -1;

    private String filters = "stat,wall,log4j";

    private boolean useGlobalDataSourceStat = false;

    private String connectionProperties;

    @Bean //声明其为Bean实例,将数据源设置为druid
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource masterDataSource() {

         DruidDataSource datasource = new DruidDataSource();

         datasource.setUrl(url);

         datasource.setUsername(username);

         datasource.setPassword(password);

         datasource.setDriverClassName(driverClassName);

         //configuration

         datasource.setInitialSize(initialSize);

         datasource.setMinIdle(minIdle);

         datasource.setMaxActive(maxActive);

         datasource.setMaxWait(maxWait);

         datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

         datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

         datasource.setValidationQuery(validationQuery);

         datasource.setTestWhileIdle(testWhileIdle);

         datasource.setTestOnBorrow(testOnBorrow);

         datasource.setTestOnReturn(testOnReturn);

         datasource.setPoolPreparedStatements(poolPreparedStatements);

         datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

         try {

         datasource.setFilters(filters);

         } catch (SQLException e) {

         System.err.println("druid configuration initialization filter: " + e);

         }

         datasource.setConnectionProperties(connectionProperties);

         return datasource;

    }

    @Bean

    @Primary

    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {

 DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();

 dataSourceTransactionManager.setDataSource(dataSource);

 return dataSourceTransactionManager;

    }

}
