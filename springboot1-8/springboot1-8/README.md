# springboot1-8 – Đăng nhập bằng email

Spring Boot 4.1.1 · Java 26 · Spring Security · Thymeleaf (fragment thường) · MapStruct · SQL Server

## Chạy dự án
1. Tạo database trong SQL Server: `CREATE DATABASE springboot1_8;`
2. Copy `.env.example` thành `.env`, sửa `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
3. Chạy: `mvn spring-boot:run` (cần JDK 26 và Maven 3.9+).
4. Mở http://localhost:8080/login

Tài khoản mẫu (tự tạo bởi `DataInitializer`): `admin@example.com` / `Admin@123`
