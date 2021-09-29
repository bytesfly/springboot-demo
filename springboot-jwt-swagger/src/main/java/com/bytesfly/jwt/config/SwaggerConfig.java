package com.bytesfly.jwt.config;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.boot.starter.autoconfigure.SpringfoxConfigurationProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ConditionalOnExpression("${springfox.documentation.enabled}")
public class SwaggerConfig {

    @Autowired(required = false)
    private SpringfoxConfigurationProperties swaggerProperties;

    public static final String HEADER_NAME = "App-Name";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JWT-SWAGGER")
                .description("JWT-SWAGGER")
                .version("1.0")
                .build();
    }

    private SecurityScheme securityScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name("JWT")
                .build();
    }

    private SecurityReference securityReference() {
        return SecurityReference.builder()
                .scopes(new AuthorizationScope[0])
                .reference("JWT")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(CollUtil.toList(securityReference()))
                .build();
    }

    private RequestParameter requestParameter() {
        return new RequestParameterBuilder()
                .name(HEADER_NAME)
                .description("")
                .in(ParameterType.HEADER)
                .required(false)
                .build();
    }

    @Bean
    public Docket frontApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(swaggerProperties.getSwaggerUi().isEnabled())
                .apiInfo(apiInfo())
                .securitySchemes(CollUtil.toList(securityScheme()))
                .securityContexts(CollUtil.toList(securityContext()))
                .globalRequestParameters(CollUtil.toList(requestParameter()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
