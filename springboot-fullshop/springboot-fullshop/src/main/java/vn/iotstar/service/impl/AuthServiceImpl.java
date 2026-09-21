package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.*;
import vn.iotstar.entity.OtpToken;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.AuthService;
import vn.iotstar.service.OtpService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    @Transactional
    public void register(RegisterDTO dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new IllegalStateException("Email da duoc su dung");
        }
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            throw new IllegalStateException("Username da duoc su dung");
        }

        Role userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail().toLowerCase())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .role(userRole)
                .enabled(false)
                .build();
        userRepository.save(user);

        otpService.generateAndSend(user.getEmail(), OtpToken.OtpType.REGISTER);
    }

    @Override
    @Transactional
    public void verifyRegisterOtp(VerifyOtpDTO dto) {
        boolean valid = otpService.verify(dto.getEmail(), dto.getCode(), OtpToken.OtpType.REGISTER);
        if (!valid) {
            throw new IllegalStateException("Ma OTP khong dung hoac da het han");
        }
        User user = userRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("Khong tim thay tai khoan"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void resendRegisterOtp(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalStateException("Khong tim thay tai khoan"));
        if (user.isEnabled()) {
            throw new IllegalStateException("Tai khoan da duoc kich hoat");
        }
        otpService.generateAndSend(user.getEmail(), OtpToken.OtpType.REGISTER);
    }

    @Override
    public void forgotPassword(ForgotPasswordDTO dto) {
        User user = userRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("Email chua duoc dang ky"));
        otpService.generateAndSend(user.getEmail(), OtpToken.OtpType.RESET_PASSWORD);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO dto) {
        boolean valid = otpService.verify(dto.getEmail(), dto.getCode(), OtpToken.OtpType.RESET_PASSWORD);
        if (!valid) {
            throw new IllegalStateException("Ma OTP khong dung hoac da het han");
        }
        User user = userRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("Khong tim thay tai khoan"));
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}
