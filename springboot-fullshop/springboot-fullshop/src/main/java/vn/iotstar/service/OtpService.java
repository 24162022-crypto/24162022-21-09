package vn.iotstar.service;

import vn.iotstar.entity.OtpToken;

public interface OtpService {

    /** Sinh OTP moi, vo hieu OTP cu cung loai + email, gui mail. */
    String generateAndSend(String email, OtpToken.OtpType type);

    /** Kiem tra OTP hop le (dung ma, con han, chua dung). Neu hop le se danh dau used=true. */
    boolean verify(String email, String code, OtpToken.OtpType type);
}
