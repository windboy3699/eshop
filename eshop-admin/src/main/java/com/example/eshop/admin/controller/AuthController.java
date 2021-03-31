package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.AuthService;
import com.example.eshop.admin.service.SystemMultiStageMenuService;
import com.example.eshop.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private SystemMultiStageMenuService systemMultiStageMenuService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDto<Object> login(@RequestBody Map<String, String> map) {
        String loginResult = authService.login(map.get("username"), map.get("password"));
        if (loginResult == null) {
            return ResponseDto.create(50101, "用户名或密码错误");
        }
        Map<String, String> data = new HashMap<>();
        data.put("accessToken", loginResult);
        return ResponseDto.create(ErrorCodeEnum.SUCCESS.getCode(), "登录成功", data);
    }

    @ResponseBody
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public ResponseDto<Object> checkLogin() {
        String token = JwtUtil.getTokenFromHeader();
        try {
            authService.checkLogin(token);
        } catch (TokenInvalidException e) {
            return ResponseDto.create(50102, "Token错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", JwtUtil.getClaim(token,"userId").asInt());
        map.put("username", JwtUtil.getClaim(token,"username").asString());
        map.put("groupId", JwtUtil.getClaim(token,"groupId").asInt());
        map.put("groupName", JwtUtil.getClaim(token,"groupName").asString());
        map.put("realname", JwtUtil.getClaim(token,"realname").asString());

        List<SystemMenuDto> menus = systemMultiStageMenuService.getByGroupId(JwtUtil.getClaim(token,"groupId").asInt());
        map.put("sideMenus", menus);

        return ResponseDto.create(0, "ok", map);
    }
}
