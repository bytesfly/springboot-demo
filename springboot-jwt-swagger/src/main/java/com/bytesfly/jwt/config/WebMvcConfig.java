package com.bytesfly.jwt.config;

import com.bytesfly.jwt.component.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 可添加请求拦截器, 静态资源路由等
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加请求拦截器
        registry.addInterceptor(requestInterceptor);
    }
}
