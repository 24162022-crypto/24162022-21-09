// File: src/main/java/vn/iotstar/dto/UserDTO.java
package vn.iotstar.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Thông tin user hiển thị ra giao diện (không chứa mật khẩu). */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    private boolean enabled;

    private LocalDateTime createdAt;

    private Long roleId;

    private String roleName;
}
