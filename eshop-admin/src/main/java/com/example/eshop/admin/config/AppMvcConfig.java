package com.example.eshop.admin.config;

import com.example.eshop.admin.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppMvcConfig implements WebMvcConfigurer {
    @Bean
    LoginHandlerInterceptor getLoginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                InterceptorRegistration ir = registry.addInterceptor(getLoginHandlerInterceptor());
                ir.addPathPatterns("/**");
                ir.excludePathPatterns("/admin/login", "/admin/dologin", "/admin/static/**");
            }
        };
    }
}
