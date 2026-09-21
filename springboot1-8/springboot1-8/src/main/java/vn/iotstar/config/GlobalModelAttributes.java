// File: src/main/java/vn/iotstar/config/GlobalModelAttributes.java
package vn.iotstar.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.UserRepository;

/**
 * Đưa thông tin user đang đăng nhập (họ tên, email, quyền...) vào model của MỌI view
 * với tên "currentUser", để fragments/header.html hiển thị được.
 * UserDetails của Spring Security chỉ chứa email + quyền nên cần truy vấn thêm để lấy họ tên.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @ModelAttribute("currentUser")
    public UserDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return userRepository.findByEmailWithRole(authentication.getName())
                .map(userMapper::toDto)
                .orElse(null);
    }
}
