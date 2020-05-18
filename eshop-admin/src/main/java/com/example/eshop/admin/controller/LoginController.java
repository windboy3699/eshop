package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResponseDto;
import com.example.eshop.admin.enums.ErrorCodeEnum;
import com.example.eshop.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto<Object> checkLogin(@RequestParam String username, @RequestParam String password) {
        Boolean loginResult = loginService.doLogin(username, password);
        if (loginResult == false) {
            return createResponseDto(50101, "用户名或密码错误");
        }
        return createResponseDto(ErrorCodeEnum.SUCCESS.getCode(), "登录成功");
    }
}
