package com.nequi.franquicias.infrastructure.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * Configuración para la base de datos R2DBC con MySQL.
 */
@Configuration
@EnableR2dbcRepositories(basePackages = "com.nequi.franquicias.infrastructure.adapter.persistence.repository")
public class DatabaseConfig {
    
    /**
     * Inicializa la base de datos con los scripts schema.sql y data.sql.
     *
     * @param connectionFactory la fábrica de conexiones R2DBC
     * @return el inicializador de la fábrica de conexiones
     */
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        populator.addScript(new ClassPathResource("data.sql"));
        
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
