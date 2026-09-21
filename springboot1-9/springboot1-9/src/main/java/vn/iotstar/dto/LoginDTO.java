// File: src/main/java/vn/iotstar/dto/LoginDTO.java
package vn.iotstar.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** login = username hoặc email. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Vui lòng nhập username hoặc email")
    private String login;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
}
