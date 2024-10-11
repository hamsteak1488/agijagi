package com.password926.agijagi.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.auth.controller.dto.request.LoginRequest;
import com.password926.agijagi.auth.service.AuthService;
import com.password926.agijagi.config.RestDocsConfig;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("로그인 성공")
    @Test
    void login() throws  Exception {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

        given(authService.login(any(), any()))
                .willReturn(1L);

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "auth/login",
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(key("constraints").value("None")),
                                        fieldWithPath("password").description("비밀번호").attributes(key("constraints").value("None"))
                                ),
                                responseFields(
                                        fieldWithPath("memberId").description("회원 아이디")
                                )
                        )
                );
    }

    @DisplayName("로그아웃 성공")
    @Test
    void logout() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/auth/logout")
                        .cookie(new Cookie("SESSION", "Session Value"))
                        .header(AuthConstants.HEADER_LOGIN_MEMBER_KEY, 1)
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "auth/logout",
                        requestCookies(
                                cookieWithName("SESSION").description("세션 쿠키")
                        ),
                        requestHeaders(
                                headerWithName(AuthConstants.HEADER_LOGIN_MEMBER_KEY).description("인증 헤더").optional()
                        )
                ));
    }

    @Test
    void whoami() {
    }

    @Test
    void error() {
    }
}