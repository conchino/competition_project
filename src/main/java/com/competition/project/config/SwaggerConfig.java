package com.competition.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)       // swagger 开启/关闭
                .groupName("分组一")
                // .enable(false)
                .select()   // select --> build /\ build --> apis/paths
                .apis(RequestHandlerSelectors.basePackage("com.competition.project.controller"))

                /*
                    RequestHandlerSelectors 设置扫描接口的方式
                    basePackage:基于包扫描
                    any(): 扫描所有
                    none(): 不扫描
                    withClassAnnotation(): 扫描类上注解
                    withMethodAnnotation(): 扫描方法上注解
                 */

                // 过滤路径
                .paths(PathSelectors.any())
                .build();
    }


    // 配置了Swagger 信息 --> apiInfo
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Chino 的 API 文档")
                .description("这里是API文档描述!")
                .version("v1.0")
                .termsOfServiceUrl("http://localhost:8080/login")
                .license("")
                .licenseUrl("")
                .build();
    }
}
