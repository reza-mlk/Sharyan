package com.example.Sharyan.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearerAuth")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .name("bearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }


    @Bean
    public OpenApiCustomizer globalResponseCustomizer(){

        return openApi -> {

            openApi.getPaths()
                    .values()
                    .forEach(pathItem ->

                            pathItem.readOperations()
                                    .forEach(operation ->

                                            operation.getResponses()
                                                    .values()
                                                    .forEach(response -> {

                                                        if(response.getContent() != null
                                                                && response.getContent().containsKey("*/*")){

                                                            response.getContent()
                                                                    .addMediaType(
                                                                            "application/json",
                                                                            response.getContent()
                                                                                    .get("*/*")
                                                                    );

                                                            response.getContent()
                                                                    .remove("*/*");
                                                        }

                                                    })
                                    )
                    );
        };
    }
}