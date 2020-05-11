package com.example.eshop.admin.controller;

import com.example.eshop.admin.service.PaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class SystemUserController extends BaseController {
    @Autowired
    private PaginatorService paginatorService;

    @RequestMapping("/system/user")
    public String index(Model model) {
        Map<String, Object> paginator = paginatorService.paging(100);
        model.addAttribute("paginator", paginator);
        return "system/user";
    }
}
