package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Username khong duoc de trong")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong hop le")
    private String email;

    @NotBlank(message = "Mat khau khong duoc de trong")
    @Size(min = 6, max = 100, message = "Mat khau toi thieu 6 ky tu")
    private String password;

    @NotBlank(message = "Ho ten khong duoc de trong")
    private String fullName;
}
