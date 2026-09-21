// File: src/main/java/vn/iotstar/config/DataInitializer.java
package vn.iotstar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

/** Chạy một lần khi ứng dụng khởi động: tạo role USER, ADMIN và 1 tài khoản admin mẫu. */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "Admin@123";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");

        if (!userRepository.existsByEmailIgnoreCase(ADMIN_EMAIL)) {
            User admin = new User();
            admin.setEmail(ADMIN_EMAIL);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setFullName("Quản trị viên");
            admin.setEnabled(true);
            admin.setRole(adminRole);
            userRepository.save(admin);
            log.info("Đã tạo tài khoản admin mẫu: {} / {}", ADMIN_EMAIL, ADMIN_PASSWORD);
        }
    }

    private Role createRoleIfNotExists(String name) {
        return roleRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> roleRepository.save(new Role(null, name)));
    }
}
