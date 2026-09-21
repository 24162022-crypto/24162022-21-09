# springboot-fullshop

Vi du 3: Users, Roles (USER/ADMIN), OtpToken, Products (1 user - n product).
Spring Boot 4.1.1 + Spring Security + MapStruct + Thymeleaf (layout-dialect) + SQL Server + Cloudinary.

## Tinh nang
- Dang ky (Register) + xac thuc OTP qua email, gui lai OTP
- Dang nhap (Login) luu session, dang nhap duoc bang username HOAC email
- Quen mat khau: gui OTP qua email, xac thuc OTP + dat mat khau moi
- CRUD User (chi ADMIN): tao/sua/xoa, tim kiem + phan trang
- CRUD Product: user thuong chi thao tac tren san pham cua minh, ADMIN thay tat ca;
  tim kiem + phan trang; upload anh len Cloudinary
- Dashboard: dem tong so user, tong so product (chi ADMIN)

## Cai dat
1. Tao database SQL Server rong (vi du: `fullshop`).
2. Copy `.env.example` thanh `.env`, dien thong tin that:
   - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
   - `MAIL_USERNAME`, `MAIL_PASSWORD` (dung App Password cua Gmail, khong dung mat khau thuong)
   - `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`
     (dang ky mien phi tai https://cloudinary.com)
   - `ADMIN_EMAIL`, `ADMIN_PASSWORD` (tai khoan admin duoc tao san khi chay lan dau)
3. Chay: `mvn spring-boot:run`
4. Truy cap: http://localhost:8082

## Tai khoan mau (tao san khi chay lan dau)
- Admin: email trong `.env` (`ADMIN_EMAIL`) / mat khau `ADMIN_PASSWORD`, da kich hoat san.
- User: username `user01`, mat khau `123456`, da kich hoat san.

## Luong Register + OTP
1. `/register` -> nhap username/email/password/fullName -> tai khoan duoc tao voi `enabled=false`,
   OTP 6 so duoc sinh va gui qua email (het han sau 5 phut, cau hinh o `app.otp.expire-minutes`).
2. `/verify-otp?email=...` -> nhap OTP -> neu dung va con han, `enabled=true`.
3. Neu chua nhan duoc mail, bam "Gui lai ma OTP" (`/register/resend-otp`).

## Luong Forgot password
1. `/forgot-password` -> nhap email -> sinh OTP loai RESET_PASSWORD, gui qua mail.
2. `/reset-password?email=...` -> nhap OTP + mat khau moi -> doi mat khau (BCrypt).

## Ghi chu ky thuat
- Neu chua co Cloudinary, co the tam thoi bo qua upload anh (form van luu duoc san pham,
  chi khong co anh) vi `CloudinaryServiceImpl.upload()` tra ve `null` khi khong co file.
- `spring.jpa.hibernate.ddl-auto=update`: bang se tu tao/cap nhat khi chay lan dau, khong can
  script SQL thu cong.
