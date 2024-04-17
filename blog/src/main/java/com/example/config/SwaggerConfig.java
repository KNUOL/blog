package com.example.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("团队名").displayName("文档标题")

                .packagesToScan("com.example.controller")
                .build()
                ;
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("blog前台接口文档")
                        .description("这时我的blog前台接口文档")
                        .version("v0.0.1")
                        .license(new License().name("knuol").url("https://www.knuol.xyz")))
                ;
    }

}
