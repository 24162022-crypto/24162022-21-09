package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Vui long nhap ma OTP")
    @Size(min = 6, max = 6)
    private String code;

    @NotBlank(message = "Mat khau moi khong duoc de trong")
    @Size(min = 6, max = 100, message = "Mat khau toi thieu 6 ky tu")
    private String newPassword;
}
