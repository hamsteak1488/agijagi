package com.password926.agijagi.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.auth.controller.dto.request.LoginRequest;
import com.password926.agijagi.auth.service.AuthService;
import com.password926.agijagi.member.controller.MemberController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

@AutoConfigureMockMvc
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
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
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
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "auth/logout"
                ));
    }

    @Test
    void whoami() {
    }

    @Test
    void error() {
    }
}