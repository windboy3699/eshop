package com.example.eshop.admin.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.eshop.admin.exception.TokenInvalidException;

public interface AuthService {
    String login(String username, String password);

    DecodedJWT checkLogin(String token) throws TokenInvalidException;
}
