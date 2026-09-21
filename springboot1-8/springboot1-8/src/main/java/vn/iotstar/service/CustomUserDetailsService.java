// File: src/main/java/vn/iotstar/service/CustomUserDetailsService.java
package vn.iotstar.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

/** Spring Security gọi lớp này khi đăng nhập: "username" chính là email. */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithRole(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy người dùng với email: " + email));

        String roleName = (user.getRole() != null) ? user.getRole().getName() : "USER";

        // Dùng User của Spring Security (khác entity vn.iotstar.entity.User) nên viết đầy đủ tên gói
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(roleName.toUpperCase())     // tự thêm tiền tố ROLE_
                .disabled(!user.isEnabled())
                .build();
    }
}
