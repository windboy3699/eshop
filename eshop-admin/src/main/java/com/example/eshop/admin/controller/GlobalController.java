package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemMultiStageMenuService;
import com.example.eshop.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private SystemMultiStageMenuService systemMultiStageMenuService;

    /**
     * 统一异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto<Object> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        return ResponseDto.create(ErrorCodeEnum.EXCEPTION_ERROR.getCode(), e.getMessage());
    }

    @ModelAttribute(name = "staticBaseUrl")
    public String staticBaseUrl() {
        return "/admin/static";
    }

    @ModelAttribute(name = "systemInfo")
    public Map<String, Object> systemInfo() {
        String token = JwtUtil.getTokenFromHeader();
        if (token == null || token == "") {
            return null;
        }
        try {
            loginService.checkLogin(token);
        } catch (TokenInvalidException e) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        List<SystemMenuDto> privMenu = systemMultiStageMenuService.getByGroupId(JwtUtil.getClaim(token,"systemGroupId").asInt());
        map.put("leftMenu", privMenu);
        map.put("systemUserId", JwtUtil.getClaim(token,"systemUserId").asInt());
        map.put("systemUsername", JwtUtil.getClaim(token,"systemUsername").asString());
        map.put("systemGroupId", JwtUtil.getClaim(token,"systemGroupId").asInt());
        map.put("systemGroupName", JwtUtil.getClaim(token,"systemGroupName").asString());
        map.put("systemRealname", JwtUtil.getClaim(token,"systemRealname").asString());
        return map;
    }

    @ModelAttribute(name = "currentUrl")
    public String currentUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = new String(request.getRequestURL());
        String queryString = request.getQueryString();
        String currentUrl = queryString == null ? url : url + "?" + queryString;
        return currentUrl;
    }

    @ModelAttribute(name = "referer")
    public String referer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader("referer");
    }
}
