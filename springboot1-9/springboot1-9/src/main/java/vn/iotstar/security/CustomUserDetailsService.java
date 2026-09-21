// File: src/main/java/vn/iotstar/security/CustomUserDetailsService.java
package vn.iotstar.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /** "username" nhận từ form có thể là username hoặc email. */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String key = (login == null) ? "" : login.trim();

        User user = userRepository.findByUsernameOrEmail(key, key)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy người dùng với username/email: " + key));

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .images(user.getImages())
                .role(user.getRole().getName())
                .enabled(user.isEnabled())
                .build();
    }
}
