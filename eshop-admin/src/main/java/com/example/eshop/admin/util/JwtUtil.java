package com.example.eshop.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.eshop.admin.exception.TokenInvalidException;
import com.example.eshop.admin.vo.AccountVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class JwtUtil {
    public static String createToken(String audience, AccountVo accountVo, String secret, Integer expiresMinutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,expiresMinutes);
        Date expiresDate = now.getTime();
        return JWT.create().withAudience(audience)
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .withClaim("systemUserId", accountVo.getSystemUserId())
                .withClaim("systemUsername", accountVo.getSystemUsername())
                .withClaim("systemGroupId", accountVo.getSystemGroupId())
                .withClaim("systemGroupName", accountVo.getSystemGroupName())
                .withClaim("systemRealname", accountVo.getSystemRealname())
                .sign(Algorithm.HMAC256(secret));
    }

    public static DecodedJWT verifyToken(String token, String secret) throws TokenInvalidException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            return verifier.verify(token);
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    public static String getAudience(String token) throws TokenInvalidException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new TokenInvalidException();
        }
        return audience;
    }

    public static Claim getClaim(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }

    public static String getTokenFromHeader() {
        String token = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            if (headerName.equalsIgnoreCase("Authorization")) {
                token = request.getHeader(headerName);
                break;
            }
        }
        if (token == null || token == "") {
            return null;
        }
        return token.substring(7);
    }
}
