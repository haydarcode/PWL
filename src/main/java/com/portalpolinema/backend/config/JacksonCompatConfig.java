package com.portalpolinema.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring Boot 4 memakai Jackson 3 (tools.jackson) sebagai default, sehingga bean
 * com.fasterxml.jackson.databind.ObjectMapper (Jackson 2) tidak lagi dibuat otomatis.
 * Beberapa kelas (PayloadParser, ApprovalRequestService) masih memakai Jackson 2,
 * jadi bean-nya didaftarkan di sini.
 */
@Configuration
public class JacksonCompatConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }
}
