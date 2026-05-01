package com.example.restaurantrating.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant Rating API")
                        .version("1.0")
                        .description("RESTful API для системы оценки ресторанов")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")));
    }
}