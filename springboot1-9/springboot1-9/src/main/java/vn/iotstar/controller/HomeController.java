// File: src/main/java/vn/iotstar/controller/HomeController.java
package vn.iotstar.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import vn.iotstar.security.CustomUserDetails;
import vn.iotstar.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
        if (principal != null) {
            // Lấy thông tin mới nhất từ DB (qua UserService + MapStruct) để hiển thị chi tiết tài khoản
            model.addAttribute("currentUser", userService.findById(principal.getId()));
        }
        return "home";
    }
}
