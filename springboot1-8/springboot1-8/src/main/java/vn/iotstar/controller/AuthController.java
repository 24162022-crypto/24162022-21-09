// File: src/main/java/vn/iotstar/controller/AuthController.java
package vn.iotstar.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.iotstar.dto.LoginDTO;

@Controller
public class AuthController {

    /**
     * Hiển thị form đăng nhập. Việc xử lý POST /login do Spring Security đảm nhiệm
     * (loginProcessingUrl), nên không cần viết @PostMapping.
     */
    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        if (loggedIn) {
            return "redirect:/dashboard"; // đã đăng nhập thì không cho vào lại trang login
        }
        model.addAttribute("loginDTO", new LoginDTO());
        return "auth/login";
    }
}
