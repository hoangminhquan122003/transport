package com.transporthc.config;

import com.transporthc.entity.Customer;
import com.transporthc.repository.CustomerRepository;
import com.transporthc.utils.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    public ApplicationRunner applicationRunner(@Qualifier("customerRepositoryImpl") CustomerRepository repository) {
        return args -> {
            if (!repository.existsByEmail("admin@gmail.com")) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                Customer customer = Customer.builder()
                        .customerName("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN.name())
                        .build();
                log.info("create successful with email: admin@gmail.com and password: admin");
                repository.save(customer);
            }
        };

    }
}
