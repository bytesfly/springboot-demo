package com.bytesfly.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户的输入不能是系统保留邮箱
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Documented
@Constraint(validatedBy = {NotSystemEmailValidator.class})
public @interface NotSystemEmail {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
