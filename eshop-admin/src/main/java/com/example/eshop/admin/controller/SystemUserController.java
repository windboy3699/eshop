package com.example.eshop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SystemUserController extends BaseController {
    @RequestMapping("/system/user")
    public String index() {
        return "system/user";
    }
}
