package com.example.eshop.admin.controller;

import com.example.eshop.admin.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class LogoutController extends BaseController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.get(request, "systemToken");
        CookieUtil.remove(response, "username");
        CookieUtil.remove(response, "token");
        stringRedisTemplate.delete(token);
        return "redirect:/admin/login";
    }
}
