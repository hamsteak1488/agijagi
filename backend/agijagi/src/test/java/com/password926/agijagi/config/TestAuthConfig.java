package com.password926.agijagi.config;

import com.password926.agijagi.auth.controller.AuthenticateInterceptor;
import com.password926.agijagi.auth.controller.LoginMemberArgumentResolver;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestAuthConfig {
    @Bean
    public LoginMemberArgumentResolver loginMemberArgumentResolver() throws Exception {
        LoginMemberArgumentResolver loginMemberArgumentResolver = mock(LoginMemberArgumentResolver.class);

        given(loginMemberArgumentResolver.supportsParameter(any(MethodParameter.class)))
                .willAnswer(invocation -> {
                    MethodParameter parameter = invocation.getArgument(0);
                    return parameter.getParameterType().equals(LoginMember.class);
                });

        given(loginMemberArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new LoginMember(1L));

        return loginMemberArgumentResolver;
    }

    @Bean
    public AuthenticateInterceptor authenticateInterceptor() throws Exception {
        AuthenticateInterceptor authenticateInterceptor = mock(AuthenticateInterceptor.class);

        given(authenticateInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);

        return authenticateInterceptor;
    }
}
