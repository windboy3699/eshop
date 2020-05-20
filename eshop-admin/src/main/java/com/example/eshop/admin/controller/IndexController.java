package com.example.eshop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class IndexController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/admin")
    public String index(Model model) {
        //通过springcloud调用远程服务
        String testInfo =  restTemplate.getForObject("http://app-eshop-service/service/product?id=123", String.class);
        model.addAttribute("testInfo", testInfo);
        return "index";
    }
}
