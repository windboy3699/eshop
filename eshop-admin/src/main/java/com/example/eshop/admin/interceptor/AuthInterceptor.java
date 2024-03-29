package com.example.eshop.admin.interceptor;

import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.AuthService;
import com.example.eshop.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String token = JwtUtil.getTokenFromHeader();
        try {
            authService.checkLogin(token);
            return true;
        } catch (TokenInvalidException e) {
            return false;
        }
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView model
    ) throws Exception{
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,Exception ex
    ) throws Exception{
    }
}
