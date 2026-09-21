// File: src/main/java/vn/iotstar/controller/HomeController.java
package vn.iotstar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    /** Trang chủ (công khai) và trang sau đăng nhập dùng chung view home.html. */
    @GetMapping({"/", "/dashboard"})
    public String home() {
        return "home";
    }

    /** Chỉ ROLE_ADMIN vào được (cấu hình trong SecurityConfig). */
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    /** Trang hiển thị khi user đã đăng nhập nhưng không đủ quyền (accessDeniedPage). */
    @GetMapping("/access-denied")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied() {
        return "error/access-denied";
    }
}
