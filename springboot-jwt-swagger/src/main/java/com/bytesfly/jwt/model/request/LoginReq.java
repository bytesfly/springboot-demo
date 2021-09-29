package com.bytesfly.jwt.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginReq {

    @ApiModelProperty(value = "用户ID")
    private String uid;

    @ApiModelProperty(value = "密码")
    private String password;
}
