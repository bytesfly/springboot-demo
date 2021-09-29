package com.bytesfly.common;

import cn.hutool.jwt.JWT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtToolTest {

    @Test
    public void t1() {
        String uid = "bytesfly";
        String token = JwtTool.generateToken(uid);
        JWT jwt = JwtTool.parseToken(token);

        assertEquals(jwt.getPayload("uid"), uid);
    }
}
