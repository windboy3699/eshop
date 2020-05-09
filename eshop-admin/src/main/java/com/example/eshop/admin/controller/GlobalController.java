package com.example.eshop.admin.controller;

import com.example.eshop.admin.dto.SystemMenuDto;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemLeftMenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private SystemLeftMenuService systemLeftMenuService;

    @ModelAttribute(name = "systemInfo")
    public Map<String, Object> leftMenu() throws JsonProcessingException {
        TokenInfoDto tokenInfoDto = loginService.checkLogin();
        if (tokenInfoDto == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        List<SystemMenuDto> leftMenu = systemLeftMenuService.get(tokenInfoDto.getSystemGroupId());
        map.put("leftMenu", leftMenu);
        map.put("systemUserId", tokenInfoDto.getSystemUserId());
        map.put("systemUsername", tokenInfoDto.getSystemUsername());
        map.put("systemGroupId", tokenInfoDto.getSystemGroupId());
        map.put("systemGroupName", tokenInfoDto.getSystemGroupName());
        map.put("systemRealname", tokenInfoDto.getSystemRealname());
        return map;
    }
}
