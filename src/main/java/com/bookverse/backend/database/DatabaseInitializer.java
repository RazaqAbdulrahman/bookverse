package com.bookverse.backend.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            log.info("==================================================");
            log.info("Database Connection Successful!");
            log.info("Database: {}", metaData.getDatabaseProductName());
            log.info("Version: {}", metaData.getDatabaseProductVersion());
            log.info("URL: {}", metaData.getURL());
            log.info("User: {}", metaData.getUserName());
            log.info("==================================================");
        } catch (Exception e) {
            log.error("Failed to connect to database: {}", e.getMessage());
            throw e;
        }
    }
}