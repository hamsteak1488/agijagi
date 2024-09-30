package com.password926.agijagi.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {
    @Value("${enable-header-auth}")
    private boolean enableHeaderAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(Authenticate.class) == null) {
            return true;
        }

        Long sessionAttribute = (Long) request.getSession().getAttribute(AuthConstants.SESSION_LOGIN_MEMBER_KEY);
        if (sessionAttribute != null) {
            request.setAttribute(AuthConstants.ATTRIBUTE_LOGIN_MEMBER_KEY, sessionAttribute);
            return true;
        }
        // 만약 헤더 인증이 허용되어 있고 Attribute가 지정되어 있다면, 헤더 인증에 성공한 것이므로 예외를 던지지 않는다.
        else if (enableHeaderAuth) {
            if (request.getAttribute(AuthConstants.ATTRIBUTE_LOGIN_MEMBER_KEY) != null) {
                return true;
            }
        }

        request.getRequestDispatcher("/auth/error/unauthenticated").forward(request, response);
        return false;
    }
}
