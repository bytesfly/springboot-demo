package com.bytesfly.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {

    // 当前请求的用户信息
    private CurrentUser currentUser;

    // 当前登录用户的token
    private String token;
}
