package com.bytesfly.common;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.RegisteredPayload;

/**
 * Jwt工具类
 * JWT token的格式：header.payload.signature
 * header的格式：
 * {"typ":"JWT","alg":"HS256"}
 * payload的格式：
 * {"exp":1632888227330,"uid":"admin"}
 * signature：
 * HmacSHA256(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
public class JwtTool {

    // 密钥
    private static final byte[] KEY = "https://github.com/bytesfly/springboot-demo".getBytes();

    private static final int TOKEN_EXPIRATION_MS = 24 * 3600 * 1000;

    public static final String PAYLOAD_UID = "uid";

    /**
     * 生成token
     */
    public static String generateToken(String uid) {
        return JWT.create()
                // 设置jwt的过期时间
                .setPayload(RegisteredPayload.EXPIRES_AT, System.currentTimeMillis() + TOKEN_EXPIRATION_MS)
                // 存储uid
                .setPayload(PAYLOAD_UID, uid)
                // 设置密钥，默认算法是：HS256(HmacSHA256)
                .setKey(KEY)
                .sign();
    }

    /**
     * 解析token
     */
    public static JWT parseToken(String token) {
        JWT jwt = JWT.of(token);
        jwt.setKey(KEY);

        if (jwt.verify()) {
            return jwt;
        } else {
            throw new JWTException("invalid token");
        }
    }
}
