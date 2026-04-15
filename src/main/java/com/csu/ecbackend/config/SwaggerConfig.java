package com.csu.ecbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
     * 1.配置生成的文档信息
     * 2.配置生成规则
     */
    @Bean
    public Docket getDocket(){

        // 指定文档风格
        // 指定生成的文档封面信息：标题、版本、作者

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("电子商务 后端接口说明")
                .description("此文档详细说明了《电子商务》项目后端接口预览")
                .version("v 2.0.1")
                .contact(new Contact("hbbbb", "www.csu.edu.cn", "1476822837@qq.com"));

        ApiInfo apiInfo = apiInfoBuilder.build();


        return new Docket(DocumentationType.SWAGGER_12)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csu.ecbackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
