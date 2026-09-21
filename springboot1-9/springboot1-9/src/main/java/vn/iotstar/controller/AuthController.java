// File: src/main/java/vn/iotstar/controller/AuthController.java
package vn.iotstar.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /** Hiển thị form đăng nhập; POST /login do Spring Security xử lý (loginProcessingUrl). */
    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/"; // đã đăng nhập thì không cho vào lại trang login
        }
        return "auth/login";
    }
}
