package com.senacor.tecco.MessageFilterService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@EnableOpenApi
@ConditionalOnProperty(value = "springfox.documentation.enabled", matchIfMissing = true)
public class SwaggerConfig {

    @Value("${info.app.version}")
    private String appVersion;
    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.description}")
    private String appDescription;

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.senacor"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                appName,
                appDescription,
                appVersion,
                "https://senacor.com/",
                new springfox.documentation.service.Contact("Marvin Heiden", "https://senacor.com/", "marvin.heiden@senacor.com"),
                "API License",
                "https://senacor.com/",
                Collections.emptyList()
        );
    }
}
