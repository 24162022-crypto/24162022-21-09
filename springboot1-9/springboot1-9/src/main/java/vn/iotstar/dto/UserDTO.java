// File: src/main/java/vn/iotstar/dto/UserDTO.java
package vn.iotstar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Thông tin user dùng để hiển thị (không chứa mật khẩu). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String username;
    private String email;
    private String fullName;
    private String images;
    private String roleName;
    private boolean enabled;
}
