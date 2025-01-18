package com.teste.itau.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI customOpenAPI() {
        // Configura o esquema de autenticação JWT
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("API de Produtos")
                        .version("1.0.0")
                        .description("Documentação da API para gerenciamento de produtos")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(securityRequirement) // Associar o esquema ao OpenAPI
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))) // Configurar o esquema como HTTP Bearer
                .externalDocs(new ExternalDocumentation()
                        .description("Mais informações")
                        .url("https://example.com"));
    }
}