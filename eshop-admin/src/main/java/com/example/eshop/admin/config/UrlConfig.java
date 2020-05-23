package com.example.eshop.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlConfig {
    @Bean
    public String appBaseUrl() {
        return "/admin";
    }

    @Bean
    public String loginUrl() {
        return appBaseUrl() + "/login";
    }
}
