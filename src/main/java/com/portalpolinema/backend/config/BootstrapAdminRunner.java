package com.portalpolinema.backend.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.portalpolinema.backend.user.User;
import com.portalpolinema.backend.user.UserRepository;

@Configuration
public class BootstrapAdminRunner {

    @Bean
    @ConditionalOnProperty(name = "app.bootstrap-admin.enabled", havingValue = "true")
    ApplicationRunner bootstrapAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@polinema.ac.id";
            if (userRepository.existsByEmail(email)) {
                System.out.println("Akun demo admin sudah tersedia: " + email);
                return;
            }

            User admin = User.builder()
                    .nama("Administrator")
                    .email(email)
                    .password(passwordEncoder.encode("Admin123!"))
                    .role(User.Role.super_admin)
                    .foto("default.png")
                    .build();

            userRepository.save(admin);
            System.out.println("Akun demo admin berhasil dibuat: " + email);
        };
    }
}
