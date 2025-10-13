package com.bookverse.backend.controller;

import com.bookverse.backend.database.DatabaseBackupService;
import com.bookverse.backend.database.DatabaseHealthCheck;
import com.bookverse.backend.database.DatabaseHelper;
import com.bookverse.backend.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/database")
@RequiredArgsConstructor
public class DatabaseAdminController {

    private final DatabaseHelper databaseHelper;
    private final DatabaseHealthCheck healthCheck;
    private final DatabaseBackupService backupService;

    /**
     * Check database health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("healthy", healthCheck.isHealthy());
        health.put("info", healthCheck.getDatabaseInfo());

        return ResponseEntity.ok(ApiResponse.success("Database health check", health));
    }

    /**
     * Get database statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = databaseHelper.getDatabaseStats();
        stats.put("databaseSize", databaseHelper.getDatabaseSize());
        stats.put("tables", databaseHelper.getAllTableNames());

        return ResponseEntity.ok(ApiResponse.success("Database statistics", stats));
    }

    /**
     * Get all table names
     */
    @GetMapping("/tables")
    public ResponseEntity<ApiResponse<List<String>>> getTables() {
        List<String> tables = databaseHelper.getAllTableNames();
        return ResponseEntity.ok(ApiResponse.success("Database tables", tables));
    }

    /**
     * Get row count for a specific table
     */
    @GetMapping("/tables/{tableName}/count")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTableCount(@PathVariable String tableName) {
        long count = databaseHelper.getTableRowCount(tableName);
        Map<String, Object> result = new HashMap<>();
        result.put("tableName", tableName);
        result.put("rowCount", count);

        return ResponseEntity.ok(ApiResponse.success("Table row count", result));
    }

    /**
     * Create database backup
     * WARNING: Requires pg_dump to be installed
     */
    @PostMapping("/backup")
    public ResponseEntity<ApiResponse<Map<String, String>>> createBackup() {
        try {
            String backupFile = backupService.createBackup();
            Map<String, String> result = new HashMap<>();
            result.put("backupFile", backupFile);
            result.put("message", "Backup created successfully");

            return ResponseEntity.ok(ApiResponse.success("Backup created", result));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Backup failed: " + e.getMessage()));
        }
    }

    /**
     * Restore database from backup
     * WARNING: This will overwrite current data
     */
    @PostMapping("/restore")
    public ResponseEntity<ApiResponse<String>> restoreBackup(@RequestParam String backupFile) {
        try {
            backupService.restoreBackup(backupFile);
            return ResponseEntity.ok(ApiResponse.success("Database restored successfully", null));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Restore failed: " + e.getMessage()));
        }
    }

    /**
     * Optimize database (VACUUM)
     */
    @PostMapping("/optimize")
    public ResponseEntity<ApiResponse<String>> optimizeDatabase() {
        databaseHelper.vacuumDatabase();
        return ResponseEntity.ok(ApiResponse.success("Database optimized successfully", null));
    }

    /**
     * Execute custom SQL query (USE WITH CAUTION!)
     */
    @PostMapping("/query")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> executeQuery(@RequestBody Map<String, String> request) {
        String sql = request.get("sql");

        if (sql == null || sql.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("SQL query is required"));
        }

        try {
            List<Map<String, Object>> results = databaseHelper.executeQuery(sql);
            return ResponseEntity.ok(ApiResponse.success("Query executed", results));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Query failed: " + e.getMessage()));
        }
    }

    /**
     * Reset database (DELETE ALL DATA - USE WITH EXTREME CAUTION!)
     */
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse<String>> resetDatabase(@RequestParam String confirm) {
        if (!"YES_DELETE_ALL_DATA".equals(confirm)) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Confirmation required: confirm=YES_DELETE_ALL_DATA"));
        }

        databaseHelper.resetDatabase();
        return ResponseEntity.ok(ApiResponse.success("Database reset completed", null));
    }
}