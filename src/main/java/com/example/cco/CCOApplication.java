package com.example.cco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class CCOApplication {
    public static void main(String[] args) {

        SpringApplication.run(CCOApplication.class, args);
    }
}
