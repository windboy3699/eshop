package com.example.eshop.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlConfig {
    @Bean
    public String appBaseUrl() { return "http://localhost:8080/admin"; }

    @Bean
    public String loginUrl() { return appBaseUrl() + "/login"; }

    @Bean
    public String imageDomainUrl() { return "http://img.static.com"; };
}
