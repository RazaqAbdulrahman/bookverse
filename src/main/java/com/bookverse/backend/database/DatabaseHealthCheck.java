package com.bookverse.backend.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseHealthCheck {

    private final DataSource dataSource;

    public boolean isHealthy() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2); // 2 second timeout
        } catch (SQLException e) {
            log.error("Database health check failed: {}", e.getMessage());
            return false;
        }
    }

    public String getDatabaseInfo() {
        try (Connection connection = dataSource.getConnection()) {
            return String.format("Database: %s, URL: %s",
                    connection.getMetaData().getDatabaseProductName(),
                    connection.getMetaData().getURL());
        } catch (SQLException e) {
            return "Unable to retrieve database info: " + e.getMessage();
        }
    }
}