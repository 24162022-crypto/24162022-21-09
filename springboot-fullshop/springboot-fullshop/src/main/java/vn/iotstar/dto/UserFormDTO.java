package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/** Dung cho admin tao/sua user trong trang quan tri (khac voi luong tu dang ky). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO {

    private Long id;

    @NotBlank(message = "Username khong duoc de trong")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong hop le")
    private String email;

    /** De trong khi sua nghia la giu nguyen mat khau cu. */
    private String password;

    @NotBlank(message = "Ho ten khong duoc de trong")
    private String fullName;

    @NotNull(message = "Vui long chon vai tro")
    private Long roleId;

    private boolean enabled;
}
