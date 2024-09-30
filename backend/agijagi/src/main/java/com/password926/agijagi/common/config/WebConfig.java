package com.password926.agijagi.common.config;

import com.password926.agijagi.auth.controller.AuthenticateInterceptor;
import com.password926.agijagi.auth.controller.HeaderAuthenticateInterceptor;
import com.password926.agijagi.auth.controller.LoginMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticateInterceptor authenticateInterceptor;
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final HeaderAuthenticateInterceptor headerAuthenticateInterceptor;

    @Value("${enable-header-auth}")
    private boolean enableHeaderAuth;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableHeaderAuth) {
            registry.addInterceptor(headerAuthenticateInterceptor);
        }
        registry.addInterceptor(authenticateInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
}
