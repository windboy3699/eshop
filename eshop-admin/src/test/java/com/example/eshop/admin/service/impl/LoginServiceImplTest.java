package com.example.eshop.admin.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceImplTest {
    @Autowired LoginServiceImpl loginService;

    @Test
    void doLogin() {
        Assert.assertEquals(Boolean.valueOf(true),loginService.doLogin("sally", "123456"));
    }
}