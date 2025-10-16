package com.bookverse.backend.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseBackupService {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private static final String BACKUP_DIR = "backups/";

    /**
     * Create a database backup (PostgreSQL)
     */
    public String createBackup() {
        try {
            // Create backup directory if not exists
            File backupDirectory = new File(BACKUP_DIR);
            if (!backupDirectory.exists()) {
                backupDirectory.mkdirs();
            }

            // Generate backup filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String backupFile = BACKUP_DIR + "bookverse_backup_" + timestamp + ".sql";

            // Extract database name from URL
            String dbName = extractDatabaseName(datasourceUrl);

            // Build pg_dump command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_dump",
                    "-h", "localhost",
                    "-p", "5432",
                    "-U", username,
                    "-F", "c", // Custom format
                    "-b", // Include large objects
                    "-v", // Verbose
                    "-f", backupFile,
                    dbName
            );

            // Set password environment variable
            processBuilder.environment().put("PGPASSWORD", password);

            // Execute backup
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Database backup created successfully: {}", backupFile);
                return backupFile;
            } else {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                String errorLine;
                StringBuilder errorMessage = new StringBuilder();
                while ((errorLine = errorReader.readLine()) != null) {
                    errorMessage.append(errorLine).append("\n");
                }
                log.error("Backup failed: {}", errorMessage);
                throw new RuntimeException("Backup failed: " + errorMessage);
            }

        } catch (IOException | InterruptedException e) {
            log.error("Error creating backup: {}", e.getMessage());
            throw new RuntimeException("Error creating backup", e);
        }
    }

    /**
     * Restore database from backup
     */
    public void restoreBackup(String backupFile) {
        try {
            String dbName = extractDatabaseName(datasourceUrl);

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pg_restore",
                    "-h", "localhost",
                    "-p", "5432",
                    "-U", username,
                    "-d", dbName,
                    "-v",
                    backupFile
            );

            processBuilder.environment().put("PGPASSWORD", password);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("Database restored successfully from: {}", backupFile);
            } else {
                throw new RuntimeException("Restore failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            log.error("Error restoring backup: {}", e.getMessage());
            throw new RuntimeException("Error restoring backup", e);
        }
    }

    @Value("${app.backup.pg-dump-path:pg_dump}")
    private String pgDumpPath;

    @Value("${app.backup.pg-restore-path:pg_restore}")
    private String pgRestorePath;

    private String extractDatabaseName(String url) {
        // Extract database name from JDBC URL
        // jdbc:postgresql://localhost:5432/bookverse_db
        String[] parts = url.split("/");
        return parts[parts.length - 1].split("\\?")[0];
    }
}
