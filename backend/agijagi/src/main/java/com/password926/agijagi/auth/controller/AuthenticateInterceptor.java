package com.password926.agijagi.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {
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

        request.getRequestDispatcher("/auth/error/unauthenticated").forward(request, response);
        return false;
    }
}
