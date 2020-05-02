package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.ResultDto;
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
    public ResultDto<Object> checkLogin(@RequestParam String username, @RequestParam String password) {
        Boolean loginResult = loginService.doLogin(username, password);
        ResultDto<Object> resultDto = new ResultDto<>();
        if (loginResult == false) {
            resultDto.setCode(101);
            resultDto.setMsg("用户或密码错误");
            return resultDto;
        }
        resultDto.setCode(0);
        resultDto.setMsg("登录成功");
        return resultDto;
    }
}
