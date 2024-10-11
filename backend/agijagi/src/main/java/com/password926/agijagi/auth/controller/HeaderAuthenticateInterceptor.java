package com.password926.agijagi.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderAuthenticateInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(Authenticate.class) == null) {
            return true;
        }

        String headerValue = request.getHeader(AuthConstants.HEADER_LOGIN_MEMBER_KEY);
        // 쿠키 인터셉터를 통해 이미 Attribute가 지정되어 있다면 헤더 인증은 동작하지 않는다.
        if (headerValue != null && request.getAttribute(AuthConstants.ATTRIBUTE_LOGIN_MEMBER_KEY) == null) {
            request.setAttribute(AuthConstants.ATTRIBUTE_LOGIN_MEMBER_KEY, Long.parseLong(headerValue));
        }

        return true;
    }
}
