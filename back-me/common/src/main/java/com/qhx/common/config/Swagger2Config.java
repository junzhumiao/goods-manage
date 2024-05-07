package com.qhx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2 // 3.0版本加不加无所谓
public class Swagger2Config {

   private boolean enabled = true;
 
    @Bean
    public Docket coreApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(adminApiInfo())
                .groupName("group1")
                .enable(enabled)
                .securityContexts(Arrays.asList(securityContexts()))
                .securitySchemes(Arrays.asList(securitySchemes()))
                .select()
                // 指定要扫描的controller包
                .apis(RequestHandlerSelectors.basePackage("com.qhx.admin.controller"))
                .build();
    }



 
    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("农产品供销管理系统--api文档")
                .description("系统接口描述")
                .version("2.0")
                .contact(new Contact("郡主喵","https://blog.csdn.net/Qhx20040819","15069680202@163.com"))
                .build();
    }

    //通过 securitySchemes 来配置全局参数，这里的配置是一个名为 Authorization 的请求头（
    //securityContexts 则用来配置有哪些请求需要携带 Token，这里我们配置了所有请求

    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}