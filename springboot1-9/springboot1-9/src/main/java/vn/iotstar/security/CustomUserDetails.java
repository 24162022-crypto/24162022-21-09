// File: src/main/java/vn/iotstar/security/CustomUserDetails.java
package vn.iotstar.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserDetails tuỳ biến: ngoài username/password còn giữ thêm id, email, fullName, images, role
 * để giao diện (header.html) lấy được qua #authentication.principal.
 *
 * Các getter getId(), getUsername(), getEmail(), getPassword(), getFullName(), getImages(),
 * getRole(), isEnabled() do Lombok @Getter sinh ra.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomUserDetails implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;

    private Long id;

    @EqualsAndHashCode.Include
    private String username;

    private String email;
    private String password;
    private String fullName;
    private String images;

    /** Tên quyền đầy đủ, ví dụ ROLE_USER. */
    private String role;

    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** Sau khi xác thực xong, xoá hash mật khẩu khỏi principal lưu trong session. */
    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
