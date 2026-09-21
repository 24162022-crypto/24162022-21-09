package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.iotstar.service.MailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtpMail(String toEmail, String otpCode, String purpose) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[IOTStar Shop] Ma OTP " + purpose);
        message.setText(
                "Xin chao,\n\n" +
                "Ma OTP cua ban la: " + otpCode + "\n" +
                "Ma co hieu luc trong 5 phut. Vui long khong chia se ma nay cho bat ky ai.\n\n" +
                "Neu ban khong yeu cau thao tac nay, vui long bo qua email.\n\n" +
                "Tran trong,\nIOTStar Shop"
        );
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Gui mail OTP that bai toi {}: {}", toEmail, e.getMessage());
        }
    }
}
