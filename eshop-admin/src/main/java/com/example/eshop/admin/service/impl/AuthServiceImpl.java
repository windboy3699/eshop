package com.example.eshop.admin.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.service.AuthService;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemUserService;
import com.example.eshop.admin.util.JwtUtil;
import com.example.eshop.admin.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AuthServiceImpl implements AuthService {
    private static final String SECRET = "PC3JNLHRBYQM76VX";

    private static final Integer EXPIRES_MINUTES = 60;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemGroupService systemGroupService;

    public String login(String username, String password) {
        String md5String = DigestUtils.md5DigestAsHex(password.getBytes());
        SystemUser systemUser = systemUserService.findByUsernameAndPassword(username, md5String);
        if (systemUser == null) {
            return null;
        }
        SystemGroup systemGroup = systemGroupService.findById(systemUser.getGroupId());
        AccountVo accountVo = new AccountVo();
        accountVo.setUserId(systemUser.getId());
        accountVo.setUsername(systemUser.getUsername());
        accountVo.setGroupId(systemUser.getGroupId());
        accountVo.setGroupName(systemGroup.getName());
        accountVo.setRealname(systemUser.getRealname());
        return JwtUtil.createToken(systemUser.getId().toString(), accountVo, SECRET, EXPIRES_MINUTES);
    }

    public DecodedJWT checkLogin(String token) throws TokenInvalidException {
        try {
            return JwtUtil.verifyToken(token, SECRET);
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }
}
