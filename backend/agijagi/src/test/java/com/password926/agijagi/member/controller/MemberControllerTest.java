package com.password926.agijagi.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.auth.controller.LoginMemberArgumentResolver;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.member.controller.dto.request.ModifyMemberRequest;
import com.password926.agijagi.member.controller.dto.request.RegisterMemberRequest;
import com.password926.agijagi.member.domain.ProfileDetail;
import com.password926.agijagi.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;
    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @DisplayName("회원가입")
    @Test
    void registerMember() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(RegisterMemberRequest.class);

        List<String> emailDescription = constraintDescriptions.descriptionsForProperty("email");
        List<String> passwordDescription = constraintDescriptions.descriptionsForProperty("password");
        List<String> nicknameDescription = constraintDescriptions.descriptionsForProperty("nickname");

        RegisterMemberRequest request = new RegisterMemberRequest("test@example.com", "password", "test");

        given(memberService.registerMember(any(ProfileDetail.class), any(String.class)))
                .willReturn(1L);

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .post("/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document(
                        "member/register-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(key("constraints").value(emailDescription)),
                                        fieldWithPath("password").description("비밀번호").attributes(key("constraints").value(passwordDescription)),
                                        fieldWithPath("nickname").description("닉네임").attributes(key("constraints").value(nicknameDescription))
                                )
                        )
                );
    }

    @DisplayName("회원 정보 조회")
    @Test
    void getProfileDetail() throws Exception {
        long memberId = 1L;

        given(memberService.getMemberProfileDetail(memberId))
                .willReturn(ProfileDetail.of("test@example.com", "test"));

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/members/{memberId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/get-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임")
                                )
                        )
                );
    }

    @DisplayName("회원 정보 변경")
    @Test
    void modifyMemberProfileDetail() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(ModifyMemberRequest.class);

        List<String> emailDescription = constraintDescriptions.descriptionsForProperty("email");
        List<String> nicknameDescription = constraintDescriptions.descriptionsForProperty("nickname");

        ModifyMemberRequest request = new ModifyMemberRequest("test@example.com", "test");

        willDoNothing().given(memberService)
                .modifyMemberProfileDetail(1L, ProfileDetail.of("test@example.com", "test"));

        given(loginMemberArgumentResolver.supportsParameter(any(MethodParameter.class)))
                .willAnswer(invocation -> {
                    MethodParameter parameter = invocation.getArgument(0);
                    return parameter.getParameterType().equals(LoginMember.class);
                });

        given(loginMemberArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new LoginMember(1L));

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/members")
                        .cookie(new Cookie("SESSION", "SESSION_ID"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/modify-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                CookieDocumentation.requestCookies(
                                        CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                                ),
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(key("constraints").value(emailDescription)),
                                        fieldWithPath("nickname").description("닉네임").attributes(key("constraints").value(nicknameDescription))
                                )
                        )
                );
    }

    @Test
    void modifyMemberProfileImage() {
    }

    @Test
    void deleteMember() {
    }
}