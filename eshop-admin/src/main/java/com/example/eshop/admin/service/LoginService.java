package com.example.eshop.admin.service;

import com.example.eshop.admin.dto.TokenInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoginService {
    Boolean doLogin(String username, String password);

    TokenInfoDto checkLogin() throws JsonProcessingException;
}
