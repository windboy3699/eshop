package com.example.eshop.admin.controller;

import com.auth0.jwt.JWT;
import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public ResponseDto<Object> doLogin(@RequestParam String username, @RequestParam String password) {
        String loginResult = loginService.doLogin(username, password);
        if (loginResult == null) {
            return ResponseDto.create(50101, "用户名或密码错误");
        }
        Map<String, String> data = new HashMap<>();
        data.put("accessToken", loginResult);
        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), "登录成功", data);
    }

    @ResponseBody
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseDto<Object> me() {
        String token = JwtUtil.getTokenFromHeader();
        try {
            loginService.checkLogin(token);
        } catch (TokenInvalidException e) {
            return ResponseDto.create(50102, "Token错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("systemUserId", JwtUtil.getClaim(token,"systemUserId").asInt());
        map.put("systemUsername", JwtUtil.getClaim(token,"systemUsername").asString());
        map.put("systemGroupId", JwtUtil.getClaim(token,"systemGroupId").asInt());
        map.put("systemGroupName", JwtUtil.getClaim(token,"systemGroupName").asString());
        map.put("systemRealname", JwtUtil.getClaim(token,"systemRealname").asString());
        return ResponseDto.create(0, "ok", map);
    }
}
