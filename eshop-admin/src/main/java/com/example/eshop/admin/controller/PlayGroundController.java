package com.example.eshop.admin.controller;

import com.example.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PlayGroundController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("sayhello")
    public String sayHello() {
        return "Hello World";
    }

    //调用本地其他Module类
    @RequestMapping("localservice")
    public String localService() {
        return userService.getUserInfo();
    }

    //通过springcloud调用远程服务
    @RequestMapping("cloudservice")
    public String cloudService() {
        return restTemplate.getForObject("http://app-eshop-service/product", String.class);
    }
}
