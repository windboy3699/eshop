package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
    @Autowired
    private LoginService loginService;

    @RequestMapping("/admin")
    public String index(Model model) throws JsonProcessingException {
        TokenInfoDto tokenInfoDto = loginService.checkLogin();
        if (tokenInfoDto == null) {
            return "redirect:/admin/login";
        }
        return "index";
    }
}
