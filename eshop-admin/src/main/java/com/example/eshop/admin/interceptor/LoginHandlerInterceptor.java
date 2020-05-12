package com.example.eshop.admin.interceptor;

import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        TokenInfoDto tokenInfoDto = loginService.checkLogin();
        if (tokenInfoDto == null) {
            response.sendRedirect("/admin/login");
            return false;
        }else {
            return true;
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
