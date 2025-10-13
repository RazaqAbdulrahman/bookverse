package com.bookverse.backend.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseHelper {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Get all table names in the database
     */
    public List<String> getAllTableNames() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * Get row count for a specific table
     */
    public long getTableRowCount(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    /**
     * Get all database statistics
     */
    public Map<String, Object> getDatabaseStats() {
        String sql = """
            SELECT 
                (SELECT COUNT(*) FROM users) as user_count,
                (SELECT COUNT(*) FROM favorites) as favorites_count,
                (SELECT COUNT(*) FROM reading_list) as reading_list_count,
                (SELECT COUNT(*) FROM reviews) as reviews_count,
                (SELECT COUNT(*) FROM books_cache) as cache_count
            """;
        return jdbcTemplate.queryForMap(sql);
    }

    /**
     * Check if a table exists
     */
    public boolean tableExists(String tableName) {
        String sql = "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, tableName);
        return exists != null && exists;
    }

    /**
     * Truncate a table (use with caution!)
     */
    public void truncateTable(String tableName) {
        log.warn("TRUNCATING TABLE: {}", tableName);
        String sql = "TRUNCATE TABLE " + tableName + " CASCADE";
        jdbcTemplate.execute(sql);
        log.info("Table {} truncated successfully", tableName);
    }

    /**
     * Reset database (delete all data, keep structure)
     */
    public void resetDatabase() {
        log.warn("RESETTING DATABASE - ALL DATA WILL BE DELETED!");

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        getAllTableNames().forEach(tableName -> {
            try {
                truncateTable(tableName);
            } catch (Exception e) {
                log.error("Failed to truncate table {}: {}", tableName, e.getMessage());
            }
        });

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");

        log.info("Database reset completed");
    }

    /**
     * Execute custom SQL query
     */
    public List<Map<String, Object>> executeQuery(String sql) {
        log.info("Executing custom query: {}", sql);
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Get database size
     */
    public String getDatabaseSize() {
        String sql = "SELECT pg_size_pretty(pg_database_size(current_database()))";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    /**
     * Vacuum database (PostgreSQL specific - optimize performance)
     */
    public void vacuumDatabase() {
        log.info("Running VACUUM on database...");
        jdbcTemplate.execute("VACUUM ANALYZE");
        log.info("VACUUM completed");
    }
}
