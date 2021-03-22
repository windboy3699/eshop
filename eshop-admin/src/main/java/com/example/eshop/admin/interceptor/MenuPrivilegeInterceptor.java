package com.example.eshop.admin.interceptor;

import com.auth0.jwt.JWT;
import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemMenu;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemMenuService;
import com.example.eshop.admin.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MenuPrivilegeInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String token = JwtUtil.getTokenFromHeader();
        Integer groupId =  JwtUtil.getClaim(token,"systemGroupId").asInt();
        if (groupId == 1) {
            return true;
        }
        SystemGroup systemGroup = systemGroupService.findById(groupId);
        List<String> strIdList = Arrays.asList(systemGroup.getMenus().split(","));
        List<Integer> intIdList = new ArrayList<>();
        for (String strId : strIdList) {
            intIdList.add(Integer.parseInt(strId));
        }
        List<SystemMenu> menuInfoList = systemMenuService.findByIdIn(intIdList);
        if (menuInfoList.isEmpty()) {
            response.sendRedirect("/admin");
            return false;
        }
        String uri = request.getRequestURI();
        for (SystemMenu item : menuInfoList) {
            if (StringUtils.isNotBlank(item.getPath()) && uri.indexOf(item.getPath()) != -1) {
                return true;
            }
        }
        response.sendRedirect("/admin");
        return false;
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
