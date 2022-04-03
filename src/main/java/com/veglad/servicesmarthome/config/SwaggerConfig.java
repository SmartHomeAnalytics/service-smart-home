package com.veglad.servicesmarthome.config;

import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.web.SwaggerTransformationContext;
import springfox.documentation.swagger2.web.WebMvcSwaggerTransformationFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.veglad.servicesmarthome.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

        return docket;
    }

    @Bean
    public WebMvcSwaggerTransformationFilter swaggerPathTransformer(@Value("${swagger.basepath}") String basePath) {
        return new SpringfoxSwaggerBasePathResolver(basePath);
    }

    private ApiInfo apiInfo() {
        String version = getClass().getPackage().getImplementationVersion();
        if (StringUtils.isEmpty(version)) {
            version = "0.1";
        }
        return new ApiInfoBuilder()
                .title("Smart Home Service")
                .version(version)
                .build();
    }

    //Can be removed after https://petabyte.atlassian.net/browse/INFRA-1019
    //ATM ingress-nginx doesn't sent X-Forwarded-Prefix header, has to detect base path from yml configs
    @Order(Ordered.LOWEST_PRECEDENCE)
    private class SpringfoxSwaggerBasePathResolver implements WebMvcSwaggerTransformationFilter {

        private final String basePath;

        public SpringfoxSwaggerBasePathResolver(String basePath) {

            this.basePath = basePath;
        }

        public Swagger transform(SwaggerTransformationContext<HttpServletRequest> context) {
            return context.getSpecification().basePath(this.basePath);
        }

        @Override
        public boolean supports(DocumentationType delimiter) {
            return DocumentationType.SWAGGER_2 == delimiter;
        }
    }
}
