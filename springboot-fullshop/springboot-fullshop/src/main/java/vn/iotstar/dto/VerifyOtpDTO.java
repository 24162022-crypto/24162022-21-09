package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Vui long nhap ma OTP")
    @Size(min = 6, max = 6, message = "Ma OTP gom 6 chu so")
    private String code;
}
