package com.bytesfly.jwt.annotation;

import com.bytesfly.jwt.component.RequestInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示 接口无须JWT认证即可访问
 * {@link RequestInterceptor}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicApi {

}
