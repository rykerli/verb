package com.red.verb.wechat.config;

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

/**
 * Swagger2
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/09     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-09 23:11
 * @date 2019-05-09 23:11
 * @since 1.0.0
 */
@EnableSwagger2
@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.red.verb.wechat.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("wechat部分RestFul API")
                //创建人
                .contact(new Contact("red", "http://localhost:8080/swagger-ui.html", "redlixh001@gmail.com"))
                //版本号
                .version("1.0")
                //描述
                .description("微信模块接口文档")
                .build();
    }
}
