package com.example.eshop.admin.service.impl;

import com.example.eshop.admin.domain.SystemGroup;
import com.example.eshop.admin.domain.SystemUser;
import com.example.eshop.admin.dto.TokenInfoDto;
import com.example.eshop.admin.service.LoginService;
import com.example.eshop.admin.service.SystemGroupService;
import com.example.eshop.admin.service.SystemUserService;
import com.example.eshop.admin.util.CookieUtil;
import com.example.eshop.admin.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String KEY = "PC3JNLHRBYQM76VX";
    private static final Integer EXPIRED = 3600;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private TokenInfoDto tokenInfoDto;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean doLogin(String username, String password) {
        String md5String = DigestUtils.md5DigestAsHex(password.getBytes());
        SystemUser systemUser = systemUserService.findByUsernameAndPassword(username, md5String);
        if (systemUser == null) {
            return false;
        }

        SystemGroup systemGroup = systemGroupService.findById(systemUser.getGroupId());

        Map<String, Object> map = new HashMap<>();
        map.put("systemUserId", systemUser.getId());
        map.put("systemUsername", systemUser.getUsername());
        map.put("systemGroupId", systemUser.getGroupId());
        map.put("systemGroupName", systemGroup.getName());
        map.put("systemRealname", systemUser.getRealname());
        map.put("time", new Date().getTime());
        map.put("key", KEY);

        String source = "";
        for(String key : map.keySet()) {
            source += key + "=" + map.get(key).toString() + "&";
        }
        source = source.substring(0, source.length() - 1);
        String token = DigestUtils.md5DigestAsHex(source.getBytes());

        tokenInfoDto.setSystemUserId(systemUser.getId());
        tokenInfoDto.setSystemUsername(systemUser.getUsername());
        tokenInfoDto.setSystemGroupId(systemUser.getGroupId());
        tokenInfoDto.setSystemGroupName(systemGroup.getName());
        tokenInfoDto.setSystemRealname(systemUser.getRealname());

        stringRedisTemplate.opsForValue().set(token, JsonUtil.toJson(tokenInfoDto), EXPIRED, TimeUnit.SECONDS);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        CookieUtil.set(response, "systemUsername", systemUser.getUsername(), EXPIRED);
        CookieUtil.set(response, "systemToken", token, EXPIRED);

        return true;
    }

    public TokenInfoDto checkLogin() throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        String systemUsername = CookieUtil.get(request, "systemUsername");
        String systemToken = CookieUtil.get(request, "systemToken");
        if (systemUsername == null || systemToken == null) {
            return null;
        }
        String tokenValue = stringRedisTemplate.opsForValue().get(systemToken);
        if (tokenValue == null) {
            return null;
        }
        TokenInfoDto tokenInfoDto = (TokenInfoDto) JsonUtil.toObject(tokenValue, TokenInfoDto.class);
        if (systemUsername.equals(tokenInfoDto.getSystemUsername()) == false) {
            return null;
        }
        return tokenInfoDto;
    }
}
