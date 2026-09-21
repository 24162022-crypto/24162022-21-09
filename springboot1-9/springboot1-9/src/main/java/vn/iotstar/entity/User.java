// File: src/main/java/vn/iotstar/entity/User.java
package vn.iotstar.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Bảng users ("user" là từ khoá của SQL Server nên đặt tên bảng là "users"). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /** Mật khẩu đã mã hoá BCrypt. */
    @Column(nullable = false)
    private String password;

    /** @Nationalized -> kiểu NVARCHAR để lưu được tiếng Việt có dấu. */
    @Nationalized
    @Column(nullable = false, length = 150)
    private String fullName;

    /** Đường dẫn ảnh đại diện, ví dụ /images/user.png hoặc /uploads/abc.jpg. */
    private String images;

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
