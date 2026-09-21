package vn.iotstar.service;

import vn.iotstar.dto.*;

public interface AuthService {

    /** Tao user (enabled=false) va gui OTP dang ky qua mail. */
    void register(RegisterDTO dto);

    /** Xac thuc OTP dang ky, kich hoat tai khoan (enabled=true). */
    void verifyRegisterOtp(VerifyOtpDTO dto);

    /** Gui lai OTP dang ky cho email chua kich hoat. */
    void resendRegisterOtp(String email);

    /** Sinh OTP quen mat khau va gui mail. */
    void forgotPassword(ForgotPasswordDTO dto);

    /** Xac thuc OTP + doi mat khau moi. */
    void resetPassword(ResetPasswordDTO dto);
}
