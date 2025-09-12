package com.nequi.franquicias.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para la documentación de la API con OpenAPI.
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .openapi("3.0.1")  // Especificar explícitamente la versión de OpenAPI
                .info(new Info()
                        .title("API de Franquicias")
                        .description("API para la gestión de franquicias, sucursales y productos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("William Moreno")
                                .email("info@nequi.com")
                                .url("https://www.nequi.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
