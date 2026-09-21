package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFormDTO {

    private Long id;

    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String name;

    private String description;

    @NotNull(message = "Vui long nhap gia")
    @DecimalMin(value = "0.0", inclusive = true, message = "Gia khong hop le")
    private BigDecimal price;

    private MultipartFile imageFile;

    /** Anh hien tai (khi sua, khong bat buoc chon anh moi). */
    private String currentImage;
}
