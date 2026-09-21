package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.OtpToken;
import vn.iotstar.repository.OtpTokenRepository;
import vn.iotstar.service.MailService;
import vn.iotstar.service.OtpService;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpTokenRepository otpTokenRepository;
    private final MailService mailService;

    @Value("${app.otp.expire-minutes:5}")
    private int expireMinutes;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateAndSend(String email, OtpToken.OtpType type) {
        // vo hieu OTP cu chua dung cung loai
        otpTokenRepository.findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(email, type)
                .ifPresent(old -> {
                    old.setUsed(true);
                    otpTokenRepository.save(old);
                });

        String code = String.format("%06d", RANDOM.nextInt(1_000_000));

        OtpToken token = OtpToken.builder()
                .email(email)
                .code(code)
                .type(type)
                .expiredAt(LocalDateTime.now().plusMinutes(expireMinutes))
                .used(false)
                .build();
        otpTokenRepository.save(token);

        String purpose = type == OtpToken.OtpType.REGISTER ? "xac thuc dang ky" : "dat lai mat khau";
        mailService.sendOtpMail(email, code, purpose);

        return code;
    }

    @Override
    public boolean verify(String email, String code, OtpToken.OtpType type) {
        return otpTokenRepository.findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(email, type)
                .filter(token -> token.getCode().equals(code))
                .filter(token -> token.getExpiredAt().isAfter(LocalDateTime.now()))
                .map(token -> {
                    token.setUsed(true);
                    otpTokenRepository.save(token);
                    return true;
                })
                .orElse(false);
    }
}
