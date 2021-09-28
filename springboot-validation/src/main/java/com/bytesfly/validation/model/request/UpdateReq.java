package com.bytesfly.validation.model.request;

import com.bytesfly.validation.validator.NotEqual;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReq {

    @NotNull(message = "ID必填")
    @NotEqual(invalidValue = "0", message = "ID为0的数据不允许更新")
    @NotEqual(invalidValue = "1", message = "ID为1的数据不允许更新")
    private Long id;

    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式不正确")
    @NotEqual(invalidValue = "system@github.com", message = "不允许填写系统保留邮箱")
    @NotEqual(invalidValue = "admin@github.com", message = "不允许填写系统保留邮箱")
    private String email;
}
