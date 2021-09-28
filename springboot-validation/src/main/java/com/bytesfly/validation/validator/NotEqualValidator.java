package com.bytesfly.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEqualValidator implements ConstraintValidator<NotEqual, Object> {

    private String invalidValue;

    @Override
    public void initialize(NotEqual annotation) {
        invalidValue = annotation.invalidValue();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            // 这里允许为null, 如果不允许为null，可以加@NotBlank或者@NotNull注解
            return true;
        }
        return !invalidValue.equals(value.toString());
    }
}
