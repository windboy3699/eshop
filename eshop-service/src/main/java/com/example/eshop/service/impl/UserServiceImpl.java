package com.example.eshop.service.impl;

import com.example.eshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String getUserInfo(Integer id) {
        return "id:" + id + ",username:Kenny";
    }
}