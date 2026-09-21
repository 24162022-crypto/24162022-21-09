package vn.iotstar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.dto.*;
import vn.iotstar.service.AuthService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // ===== REGISTER =====

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDTO registerDTO,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            authService.register(registerDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
        return "redirect:/verify-otp?email=" + registerDTO.getEmail();
    }

    @GetMapping("/verify-otp")
    public String verifyOtpForm(@RequestParam String email, Model model) {
        VerifyOtpDTO dto = new VerifyOtpDTO();
        dto.setEmail(email);
        model.addAttribute("verifyOtpDTO", dto);
        return "auth/verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@Valid @ModelAttribute VerifyOtpDTO verifyOtpDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/verify-otp";
        }
        try {
            authService.verifyRegisterOtp(verifyOtpDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/verify-otp";
        }
        return "redirect:/login?verified=true";
    }

    @PostMapping("/register/resend-otp")
    public String resendOtp(@RequestParam String email, Model model) {
        try {
            authService.resendRegisterOtp(email);
            model.addAttribute("successMessage", "Da gui lai ma OTP, vui long kiem tra email.");
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        VerifyOtpDTO dto = new VerifyOtpDTO();
        dto.setEmail(email);
        model.addAttribute("verifyOtpDTO", dto);
        return "auth/verify-otp";
    }

    // ===== FORGOT PASSWORD =====

    @GetMapping("/forgot-password")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute ForgotPasswordDTO forgotPasswordDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/forgot-password";
        }
        try {
            authService.forgotPassword(forgotPasswordDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/forgot-password";
        }
        return "redirect:/reset-password?email=" + forgotPasswordDTO.getEmail();
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam String email, Model model) {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setEmail(email);
        model.addAttribute("resetPasswordDTO", dto);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute ResetPasswordDTO resetPasswordDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/reset-password";
        }
        try {
            authService.resetPassword(resetPasswordDTO);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/reset-password";
        }
        return "redirect:/login?reset=true";
    }
}
