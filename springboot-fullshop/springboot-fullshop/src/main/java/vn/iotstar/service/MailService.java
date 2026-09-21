package vn.iotstar.service;

public interface MailService {

    void sendOtpMail(String toEmail, String otpCode, String purpose);
}
