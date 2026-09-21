// File: src/main/java/vn/iotstar/Springboot19Application.java
package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@SpringBootApplication
public class Springboot19Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot19Application.class, args);
    }

    /**
     * DataInitializer: chạy một lần khi ứng dụng khởi động.
     * Tạo role ROLE_USER và user mẫu user01 / user01@gmail.com / 123456 (nếu chưa có).
     */
    @Bean
    CommandLineRunner dataInitializer(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

            boolean exists = userRepository.findByUsername("user01").isPresent()
                    || userRepository.findByEmail("user01@gmail.com").isPresent();

            if (!exists) {
                userRepository.save(User.builder()
                        .username("user01")
                        .email("user01@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .fullName("Nguyễn Hữu Trung")
                        .images("/images/user.png")
                        .enabled(true)
                        .role(userRole)
                        .build());
                System.out.println(">>> Đã tạo user mẫu: user01 / user01@gmail.com (mật khẩu: 123456)");
            }
        };
    }
}
