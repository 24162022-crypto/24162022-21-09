// File: src/main/java/vn/iotstar/config/ThymeleafConfig.java
package vn.iotstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

/** Đăng ký thymeleaf-layout-dialect (layout:decorate, layout:fragment, layout:title-pattern). */
@Configuration
public class ThymeleafConfig {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
