package com.example.eshop.admin.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceImplTest {
    @Autowired AuthServiceImpl authService;

    @Test
    void doLogin() {
        Assert.assertEquals(Boolean.valueOf(true),authService.login("sally", "123456"));
    }
}