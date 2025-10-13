package com.bookverse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BookverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookverseApplication.class, args);
    }

}
