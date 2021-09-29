package com.bytesfly.jwt.controller;

import cn.hutool.core.util.StrUtil;
import com.bytesfly.common.JwtTool;
import com.bytesfly.jwt.annotation.PublicApi;
import com.bytesfly.jwt.component.Context;
import com.bytesfly.jwt.model.CurrentUser;
import com.bytesfly.jwt.model.request.LoginReq;
import com.bytesfly.validation.model.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"测试JWT接口"})
@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @PublicApi
    @ApiOperation(value = "登录获取token")
    @PostMapping("login")
    public ApiResponse<String> login(@RequestBody LoginReq req) {
        String uid = req.getUid();
        String password = req.getPassword();
        // 模拟查询缓存或者db，password 等于 uid+"0123" 就认为登录成功
        if (StrUtil.isNotBlank(uid)
                && StrUtil.isNotBlank(password)
                && password.equals(uid + "0123")) {
            String token = JwtTool.generateToken(uid);
            return ApiResponse.successful(token);
        } else {
            return ApiResponse.failed("密码错误", HttpStatus.BAD_REQUEST.value());
        }
    }

    @ApiOperation(value = "获取当前登录用户(当然也可以检测token合法性)")
    @GetMapping("me")
    public ApiResponse<CurrentUser> me() {
        CurrentUser user = Context.currentUser();
        return ApiResponse.successful(user);
    }
}
