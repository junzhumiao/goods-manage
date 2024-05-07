package com.qhx.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {"com.qhx.common","com.qhx.admin"})
@EnableConfigurationProperties(
        value = {com.qhx.admin.config.fisco.ContractConfig.class}
)
public class AdminApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AdminApplication.class, args);
    }

}
