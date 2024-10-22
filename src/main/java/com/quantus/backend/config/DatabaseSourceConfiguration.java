package com.quantus.backend.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-05-17
 */
@Configuration
public class DatabaseSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.x-x")
    public DataSourceProperties PrimaryDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource PrimaryDatasource() {
        return PrimaryDatasourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
