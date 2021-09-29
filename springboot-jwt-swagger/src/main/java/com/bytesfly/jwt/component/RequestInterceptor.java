package com.bytesfly.jwt.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.RegisteredPayload;
import com.bytesfly.common.JwtTool;
import com.bytesfly.common.exception.AuthException;
import com.bytesfly.jwt.annotation.PublicApi;
import com.bytesfly.jwt.config.SwaggerConfig;
import com.bytesfly.jwt.model.CurrentUser;
import com.bytesfly.jwt.model.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 请求拦截器
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private ServerProperties serverProperties;

    private String prefix = "/api";

    @PostConstruct
    private void init() {
        // 获取后端接口起始路径
        prefix = serverProperties.getServlet().getContextPath() + "/api";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", request.getMethod());
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setStatus(HttpStatus.OK.value());
            return true;
        }

        if (request.getRequestURI().startsWith(prefix) && needJwt(handler)) {
            // 只对后端api请求且需要认证的接口做身份认证
            // 默认所有的后端api请求都需要身份认证，除非接口上显示注解了@PublicApi，比如登录接口

            // 从Http Header中取Authorization
            String tokenHeader = request.getHeader("Authorization");
            if (StrUtil.isBlank(tokenHeader)) {
                throw new AuthException("身份验证失败");
            }
            // 截取'Bearer '后的内容, 获取纯粹的token
            String token = tokenHeader.substring("Bearer ".length());
            try {
                JWT jwt = JwtTool.parseToken(token);
                Long expiresAt = (Long) jwt.getPayload(RegisteredPayload.EXPIRES_AT);
                if (expiresAt < System.currentTimeMillis()) {
                    throw new AuthException("token过期");
                }
                String uid = (String) jwt.getPayload(JwtTool.PAYLOAD_UID);
                // 将用户信息放入 上下文环境中
                Context.THREAD_LOCAL.set(new RequestContext(new CurrentUser(uid, expiresAt), token));
                log.info("uid: {}, appName: {}, {}:{}", uid, request.getHeader(SwaggerConfig.HEADER_NAME),
                        request.getMethod(), request.getRequestURI());
            } catch (AuthException e) {
                throw e;
            } catch (Throwable e) {
                throw new AuthException("身份验证失败");
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) {
        Context.THREAD_LOCAL.remove();
    }

    /**
     * 接口是否需要JWT认证
     *
     * @return true表示需要JWT认证, false表示不需要
     */
    private boolean needJwt(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        PublicApi publicApi = method.getAnnotation(PublicApi.class);
        return publicApi == null;
    }
}
