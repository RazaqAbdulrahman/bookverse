package com.bookverse.backend.database;

import com.bookverse.backend.model.User;
import com.bookverse.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev") // Only runs in 'dev' profile
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            log.info("Seeding database with test users...");

            // Create test user 1
            User user1 = User.builder()
                    .email("admin@bookverse.com")
                    .password(passwordEncoder.encode("admin123"))
                    .build();
            userRepository.save(user1);

            // Create test user 2
            User user2 = User.builder()
                    .email("user@bookverse.com")
                    .password(passwordEncoder.encode("user123"))
                    .build();
            userRepository.save(user2);

            log.info("Database seeded with {} users", userRepository.count());
            log.info("Test Users Created:");
            log.info("  - admin@bookverse.com / admin123");
            log.info("  - user@bookverse.com / user123");
        } else {
            log.info("Database already contains {} users. Skipping seeding.", userRepository.count());
        }
    }
}