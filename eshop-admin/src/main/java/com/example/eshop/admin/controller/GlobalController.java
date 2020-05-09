package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemLeftMenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private SystemLeftMenuService systemLeftMenuService;

    @ModelAttribute(name = "leftMenu")
    public List<SystemMenuDto> leftMenu() throws JsonProcessingException {
        TokenInfoDto tokenInfoDto = loginService.checkLogin();
        if (tokenInfoDto == null) {
            return null;
        }
        List<SystemMenuDto> list = systemLeftMenuService.get(tokenInfoDto.getSystemGroupId());
        return list;
    }
}
