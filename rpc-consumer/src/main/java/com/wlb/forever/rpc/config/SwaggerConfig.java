package com.wlb.forever.rpc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
 * @Auther: william
 * @Date: 18/10/31 09:16
 * @Description:
 */

@Configuration
@EnableSwagger2
@ConditionalOnExpression("'${swagger.enable}' == 'true'")
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wlb.forever.rpc.client.business.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("test")
                .description("test")
                .termsOfServiceUrl("http:www.baidu.com")
                .contact(new Contact("kazusa","http:www.baidu.com","williamzgzc@gmail.com"))
                .version("1.0.0")
                .build();
    }
}
