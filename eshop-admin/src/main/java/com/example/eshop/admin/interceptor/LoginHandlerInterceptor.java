package com.example.eshop.admin.interceptor;

import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String token = JwtUtil.getTokenFromHeader();
        try {
            loginService.checkLogin(token);
            return true;
        } catch (TokenInvalidException e) {
            response.sendRedirect("/admin/login");
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
