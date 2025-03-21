package com.restaurant.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant API")
                        .description("API for managing restaurants, menus, and reviews")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Restaurant Service")
                                .email("info@restaurant-service.com")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")
                ));
    }
} 