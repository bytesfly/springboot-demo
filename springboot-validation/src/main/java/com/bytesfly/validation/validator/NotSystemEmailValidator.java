package com.bytesfly.validation.validator;

import com.bytesfly.validation.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSystemEmailValidator implements ConstraintValidator<NotSystemEmail, Object> {

    @Autowired
    private EmailService emailService;

    @Override
    public boolean isValid(Object email, ConstraintValidatorContext context) {
        if (email == null) {
            // 这里允许为null, 如果不允许为null，可以加@NotBlank或者@NotNull注解
            return true;
        }
        return !emailService.isSystemEmail(email.toString());
    }
}
