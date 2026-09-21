# springboot1-9 – Đăng nhập bằng username hoặc email

Spring Boot 4.1.1 · Java 26 · Spring Security 7 · Thymeleaf + thymeleaf-layout-dialect · MapStruct · SQL Server

## Chạy dự án
1. Tạo database trong SQL Server: `CREATE DATABASE springboot1_9;`
2. Sửa `spring.datasource.username/password` trong `src/main/resources/application.properties`.
3. Chạy `mvn spring-boot:run` (cần JDK 26 và Maven 3.9+), mở http://localhost:8081/login

Tài khoản mẫu (tự tạo lúc khởi động): `user01` hoặc `user01@gmail.com` / `123456`
