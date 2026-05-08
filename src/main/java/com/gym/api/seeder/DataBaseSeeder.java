package com.gym.api.seeder;

import com.gym.api.entity.Role;
import com.gym.api.entity.User;
import com.gym.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Starting database seeding...");
            seedUsers();
            log.info("Database seeding completed!");
        } else {
            log.info("Data already exists, skipping seeder");
        }
    }

    private void seedUsers() {
        User admin = User.builder()
                .name("admin")
                .email("asdf@gmail.com")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("asdfasdf"))
                .phoneNumber("9814933683")
                .isActive(true)
                .build();

        User regularUser = User.builder()
                .name("user")
                .email("user@gmail.com")
                .role(Role.USER)
                .password(passwordEncoder.encode("asdfasdf"))
                .phoneNumber("9816023479")
                .isActive(true)
                .build();

        userRepository.saveAll(Arrays.asList(admin, regularUser));
        log.info("Created {} users", userRepository.count());
    }
}
