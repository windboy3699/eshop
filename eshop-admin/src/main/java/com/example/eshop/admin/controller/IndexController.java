package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/admin")
    public String index(Model model) throws JsonProcessingException {
        TokenInfoDto tokenInfoDto = loginService.checkLogin();
        if (tokenInfoDto == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("systemUserId", tokenInfoDto.getSystemUserId());
        model.addAttribute("systemUsername", tokenInfoDto.getSystemUsername());
        model.addAttribute("systemGroupId", tokenInfoDto.getSystemGroupId());
        model.addAttribute("systemRealname", tokenInfoDto.getSystemRealname());
        return "index";
    }
}
