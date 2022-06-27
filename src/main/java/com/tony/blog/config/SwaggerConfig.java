package com.tony.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 // 开启swagger2
public class SwaggerConfig {

    // 配置swagger的docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // RequestHandlerSelectors，配置扫描接口方式
                // basePackage，指定扫描的包
                // any(), 扫描全部
                // none(), 不扫描
                // withClassAnnotation，扫描类上注解，参数是一个注解的反射对象
                // withMethodAnnotation，扫描方法上注解
                .apis(RequestHandlerSelectors.basePackage("com.tony.blog.controller"))
                // paths() // 过滤什么路径
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    // 配置swagger 信息 = apiInfo
    private ApiInfo apiInfo(){
        Contact contact = new Contact("tony", "https://www.bilibili.com/", "1605337475@qq.com");
        return new ApiInfo(
                "tony的swagger API 文档"
                , "with great powder, comes great responsibility!"
                , "1.0"
                , "urn:tos"
                , contact
                , "Apache 2.0"
                , "http://www.apache.org/licenses/LICENSE-2.0"
                , new ArrayList());
    }


}