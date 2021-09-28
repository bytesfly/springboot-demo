package com.bytesfly.validation.model.request;

import com.bytesfly.validation.validator.NotSystemEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateReq {

    @NotNull(message = "年龄必填")
    @Min(value = 18, message = "年龄必须18周岁及以上")
    @Max(value = 36, message = "年龄必须36周岁及以下")
    private Integer age;

    @NotBlank(message = "手机号必填")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号必须1开头，且11位")
    private String phone;

    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式不正确")
    @NotSystemEmail(message = "不允许填写系统保留邮箱")
    private String email;

    @NotBlank(message = "密码必填")
    @Length(min = 8, max = 20, message = "密码长度8~20")
    private String password;
}
