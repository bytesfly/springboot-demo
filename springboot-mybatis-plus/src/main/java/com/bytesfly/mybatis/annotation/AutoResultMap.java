package com.bytesfly.mybatis.annotation;

import com.bytesfly.mybatis.config.MybatisPlusConfig;

import java.lang.annotation.*;

/**
 * 使用@AutoResultMap注解的实体类
 * 自动生成{auto.mybatis-plus_类名}为id的resultMap
 * {@link MybatisPlusConfig#initAutoResultMap()}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoResultMap {

}
