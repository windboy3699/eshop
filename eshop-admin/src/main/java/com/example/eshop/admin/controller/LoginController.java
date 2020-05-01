package com.example.eshop.admin.controller;

import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value = "/checklogin", method = RequestMethod.POST)
    @ResponseBody
    public String checkLogin(@RequestParam String username, @RequestParam String password) {
        String md5String = DigestUtils.md5DigestAsHex(password.getBytes());
        SystemUser systemUser = systemUserService.findByUsernameAndPassword(username, md5String);
        if (systemUser == null) {
            return "loginfail";
        }
        return "loginsuccess";
    }
}
