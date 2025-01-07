package com.teste.itau.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class Swagger {

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI()
                .info(new Info()
                        .title("api--teste-itau")
                        .version("1.0")
                        .description("API para gerenciar produtos")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
