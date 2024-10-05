package com.password926.agijagi.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.config.TestAuthConfig;
import com.password926.agijagi.config.RestDocsConfig;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.member.controller.dto.request.ModifyMemberRequest;
import com.password926.agijagi.member.controller.dto.request.RegisterMemberRequest;
import com.password926.agijagi.member.domain.MemberDetail;
import com.password926.agijagi.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

@Import({RestDocsConfig.class, TestAuthConfig.class})
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원가입")
    @Test
    void registerMember() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(RegisterMemberRequest.class);

        List<String> emailDescription = constraintDescriptions.descriptionsForProperty("email");
        List<String> passwordDescription = constraintDescriptions.descriptionsForProperty("password");
        List<String> nicknameDescription = constraintDescriptions.descriptionsForProperty("nickname");

        given(memberService.registerMember(anyString(), anyString(), anyString(), any(MediaResource.class)))
                .willReturn(1L);

        MockPart emailPart = new MockPart("email", "test@example.com".getBytes());
        MockPart passwordPart = new MockPart("password", "password".getBytes());
        MockPart nicknamePart = new MockPart("nickname", "test".getBytes());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("profileImage", "profile.png", "image/png", InputStream.nullInputStream());

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .multipart("/members")
                                .file(mockMultipartFile)
                                .part(emailPart, passwordPart, nicknamePart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document(
                        "member/register-member",
                                requestParts(
                                        partWithName("email").description("이메일").attributes(key("constraints").value(emailDescription)),
                                        partWithName("password").description("비밀번호").attributes(key("constraints").value(passwordDescription)),
                                        partWithName("nickname").description("닉네임").attributes(key("constraints").value(nicknameDescription)),
                                        partWithName("profileImage").description("프로필 이미지").attributes(key("constraints").value("None")).optional()
                                )
                        )
                );
    }

    @DisplayName("회원 정보 변경")
    @Test
    void updateMember() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(ModifyMemberRequest.class);

        List<String> emailDescription = constraintDescriptions.descriptionsForProperty("email");
        List<String> passwordDescription = constraintDescriptions.descriptionsForProperty("password");
        List<String> nicknameDescription = constraintDescriptions.descriptionsForProperty("nickname");

        ModifyMemberRequest request = new ModifyMemberRequest("test@example.com", "password", "test");

        willDoNothing().given(memberService)
                .updateMember(anyLong(), anyString(), anyString(), anyString());

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/members")
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/modify-member",
                                requestCookies(
                                        CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                                ),
                                requestFields(
                                        fieldWithPath("email").description("이메일").attributes(key("constraints").value(emailDescription)).optional(),
                                        fieldWithPath("password").description("비밀번호").attributes(key("constraints").value(passwordDescription)).optional(),
                                        fieldWithPath("nickname").description("닉네임").attributes(key("constraints").value(nicknameDescription)).optional()
                                )
                        )
                );
    }

    @Test
    void updateMemberProfileImage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("profileImage", "profile.png", "image/png", InputStream.nullInputStream());

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .multipart("/members/profile-image/update")
                                .file(mockMultipartFile)
                                .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/modify-member-profile-image",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        requestParts(
                                partWithName("profileImage").description("새 프로필 이미지").attributes(key("constraints").value("None"))
                        )
                ));
    }

    @Test
    void deleteMemberProfileImage() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/members/profile-image/delete")
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/delete-member-profile-image",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        )
                ));
    }

    @Test
    void deleteMember() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/members")
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "member/delete-member",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        )
                ));
    }

    @DisplayName("회원 정보 조회")
    @Test
    void getMemberDetail() throws Exception {
        long memberId = 1L;

        given(memberService.getMemberDetail(memberId))
                .willReturn(MemberDetail.builder()
                        .memberId(memberId)
                        .email("test@example.com")
                        .nickname("test")
                        .profileImageUrl("test.image-url.com")
                        .build());

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/members/{memberId}", 1)
                                .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                                .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                                "member/get-member",
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").description("회원 ID"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("profileImageUrl").description("프로필 이미지 URL")
                                )
                        )
                );
    }
}