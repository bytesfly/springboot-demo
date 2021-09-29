package com.bytesfly.jwt.component;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.bytesfly.jwt.model.CurrentUser;
import com.bytesfly.jwt.model.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 运行时上下文环境(从Spring容器中获取Bean，获取当前登录用户等)
 */
@Component
public class Context implements ApplicationContextAware {

    public static final TransmittableThreadLocal<RequestContext> THREAD_LOCAL = new TransmittableThreadLocal<>();

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Context.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static RequestContext ctx() {
        return THREAD_LOCAL.get();
    }

    /**
     * 返回当前请求的用户信息
     */
    public static CurrentUser currentUser() {
        RequestContext ctx = ctx();
        return ctx != null ? ctx.getCurrentUser() : null;
    }
}
