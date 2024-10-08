package com.password926.agijagi.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.config.RestDocsConfig;
import com.password926.agijagi.config.TestAuthConfig;
import com.password926.agijagi.post.controller.dto.request.CreateCommentRequest;
import com.password926.agijagi.post.controller.dto.request.UpdateCommentRequest;
import com.password926.agijagi.post.domain.CommentDetail;
import com.password926.agijagi.post.service.CommentService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

@Import({RestDocsConfig.class, TestAuthConfig.class})
@AutoConfigureRestDocs
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @DisplayName("댓글 생성")
    @Test
    void createComment() throws Exception {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L, "This is comment");

        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(CreateCommentRequest.class);
        List<String> contentDescription = constraintDescriptions.descriptionsForProperty("content");

        given(commentService.createComment(anyLong(), anyLong(), anyLong(), anyString()))
                .willReturn(2L);

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/posts/{postId}/comments", 1L)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .content(objectMapper.writeValueAsString(createCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON)

                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document(
                        "comment/create-comment",
                        requestCookies(
                                cookieWithName("SESSION").description("세션 ID")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("parentCommentId").description("부모 댓글 ID").attributes(key("constraints").value("None")).optional(),
                                fieldWithPath("content").description("댓글 내용").attributes(key("constraints").value(contentDescription))
                        )
                ));
    }

    @DisplayName("댓글 수정")
    @Test
    void updateComment() throws Exception {
        UpdateCommentRequest request = new UpdateCommentRequest("updated comment content");

        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(UpdateCommentRequest.class);
        List<String> contentDescription = constraintDescriptions.descriptionsForProperty("content");

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .patch("/comments/{commentId}", 1L)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "comment/update-comment",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").description("댓글 내용").attributes(key("constraints").value(contentDescription)).optional()
                        )
                ));
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/comments/{commentId}", 1L)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "comment/delete-comment",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        )
                ));
    }

    @DisplayName("게시글 댓글 목록 조회")
    @Test
    void getPostComments() throws Exception {
        List<CommentDetail> commentDetails = new ArrayList<>();

        int commentCount = 3;
        for (int i = 1; i<= commentCount; i++) {
            commentDetails.add(CommentDetail.builder()
                    .commentId(i)
                    .parentCommentId(null)
                    .writerId(1L)
                    .writerNickname("작성자" + i)
                    .content("내용" + i)
                    .createdAt(LocalDateTime.now())
                    .build()
            );
        }

        given(commentService.getPostCommentDetails(anyLong()))
                .willReturn(commentDetails);

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/posts/{postId}/comments", 1L)
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "comment/get-post-comments",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                ));
    }
}
