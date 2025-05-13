package com.example.juniebookmarks.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookmarksOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookmarks API")
                        .description("API for managing bookmarks")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Junie Bookmarks Team")
                                .email("support@juniebookmarks.example.com")));
    }
}