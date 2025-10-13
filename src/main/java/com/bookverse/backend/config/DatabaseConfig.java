package com.bookverse.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@Slf4j
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");

        // Connection Pool Settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(20000); // 20 seconds
        config.setIdleTimeout(300000); // 5 minutes
        config.setMaxLifetime(1200000); // 20 minutes

        // Performance Settings
        config.setAutoCommit(true);
        config.setConnectionTestQuery("SELECT 1");

        // Logging
        config.setPoolName("BookverseHikariCP");
        config.setLeakDetectionThreshold(60000); // 60 seconds

        log.info("Initializing HikariCP connection pool");
        log.info("JDBC URL: {}", jdbcUrl);
        log.info("Max Pool Size: {}", config.getMaximumPoolSize());

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}