package com.crossyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能: Swagger2 配置
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("天天吃货")
                        .contact(new Contact("crossyf", "", ""))
                        .description("天天吃货电商项目后端api文档")
                        .version("1.0.0").build())
                .select().apis(RequestHandlerSelectors.basePackage("com.crossyf.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
