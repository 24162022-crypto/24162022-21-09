package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email}") String adminEmail,
            @Value("${app.admin.password}") String adminPassword
    ) {
        return args -> {
            Role userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));
            Role adminRole = roleRepository.findByNameIgnoreCase("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));

            if (!userRepository.existsByEmailIgnoreCase(adminEmail)) {
                User admin = User.builder()
                        .username("admin")
                        .email(adminEmail.toLowerCase())
                        .password(passwordEncoder.encode(adminPassword))
                        .fullName("System Administrator")
                        .role(adminRole)
                        .enabled(true)
                        .build();
                userRepository.save(admin);
            }

            if (!userRepository.existsByUsernameIgnoreCase("user01")) {
                User demo = User.builder()
                        .username("user01")
                        .email("user01@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Nguyen Huu Trung")
                        .role(userRole)
                        .enabled(true)
                        .build();
                userRepository.save(demo);
            }
        };
    }
}
