package com.example.eshop.admin.config;

import com.example.eshop.admin.interceptor.LoginHandlerInterceptor;
import com.example.eshop.admin.interceptor.MenuPrivilegeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EshopMvcConfig implements WebMvcConfigurer {
    @Bean
    LoginHandlerInterceptor getLoginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }

    @Bean
    MenuPrivilegeInterceptor getMenuPrivilegeInterceptor() { return new MenuPrivilegeInterceptor(); }

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                InterceptorRegistration loginRegistration = registry.addInterceptor(getLoginHandlerInterceptor());
                loginRegistration.addPathPatterns("/**");
                loginRegistration.excludePathPatterns("/admin/login", "/admin/dologin", "/admin/logout", "/admin/static/**");

                InterceptorRegistration privRegistration = registry.addInterceptor(getMenuPrivilegeInterceptor());
                privRegistration.addPathPatterns("/**");
                privRegistration.excludePathPatterns("/admin", "/admin/login", "/admin/dologin", "/admin/logout", "/admin/static/**");
            }
        };
    }
}
