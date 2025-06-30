package com.example.mvc;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import org.mockito.Mockito;

@TestConfiguration
public class TestDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // Create a dummy DataSource (e.g., a mock)
        return Mockito.mock(DataSource.class);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
