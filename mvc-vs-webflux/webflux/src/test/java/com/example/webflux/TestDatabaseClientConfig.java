package com.example.webflux;

import io.r2dbc.spi.ConnectionFactory;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

@TestConfiguration
public class TestDatabaseClientConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        // Provide a mock ConnectionFactory so DatabaseClient doesn't fail
        return Mockito.mock(ConnectionFactory.class);
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }
}
