package com.password926.agijagi.post.controller;

import com.password926.agijagi.config.TestAuthConfig;
import com.password926.agijagi.config.RestDocsConfig;
import com.password926.agijagi.post.controller.dto.request.CreatePostRequest;
import com.password926.agijagi.post.controller.dto.request.UpdatePostRequest;
import com.password926.agijagi.post.domain.PostDetail;
import com.password926.agijagi.post.service.PostService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

@Import({RestDocsConfig.class, TestAuthConfig.class})
@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @DisplayName("게시글 생성")
    @Test
    void createPost() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(CreatePostRequest.class);

        List<String> titleDescription = constraintDescriptions.descriptionsForProperty("title");
        List<String> contentDescription = constraintDescriptions.descriptionsForProperty("content");

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("mediaList", "image1.png", "image/png", InputStream.nullInputStream());
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("mediaList", "video1.mp4", "video/mp4", InputStream.nullInputStream());

        given(postService.createPost(anyLong(), anyString(), anyString(), anyList()))
                .willReturn(1L);

        MockPart titlePart = new MockPart("title", "title 1".getBytes());
        MockPart contentPart = new MockPart("content", "content 1".getBytes());

        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .multipart("/posts")
                        .file(mockMultipartFile1)
                        .file(mockMultipartFile2)
                        .part(titlePart, contentPart)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document(
                        "post/create-post",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        requestParts(
                                partWithName("title").description("제목").attributes(key("constraints").value(titleDescription)),
                                partWithName("content").description("내용").attributes(key("constraints").value(contentDescription)),
                                partWithName("mediaList").description("미디어 목록").attributes(key("constraints").value("None")).optional()
                        ))
                );
    }

    @Test
    void updatePost() throws Exception {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(UpdatePostRequest.class);

        List<String> titleDescription = constraintDescriptions.descriptionsForProperty("title");
        List<String> contentDescription = constraintDescriptions.descriptionsForProperty("content");

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("newMediaList", "image1.png", "image/png", InputStream.nullInputStream());
        MockPart titlePart = new MockPart("title", "Updated Title".getBytes());
        MockPart contentPart = new MockPart("content", "Updated Content".getBytes());
        MockPart deleteMediaIdListPart = new MockPart("deleteMediaIdList", "abcdabcd-abcd-abcd-abcd-abcdabcdabcd,dcbadcba-dcba-dcba-dcba-dcbadcbadcba".getBytes());
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .multipart("/posts/{postId}", 1)
                        .file(mockMultipartFile1)
                        .part(titlePart, contentPart, deleteMediaIdListPart)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "post/update-post",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        requestParts(
                                partWithName("title").description("수정할 제목").attributes(key("constraints").value(titleDescription)).optional(),
                                partWithName("content").description("수정할 내용").attributes(key("constraints").value(contentDescription)).optional(),
                                partWithName("newMediaList").description("새로 추가할 미디어 목록").attributes(key("constraints").value("None")).optional(),
                                partWithName("deleteMediaIdList").description("삭제할 미디어 아이디 목록").attributes(key("constraints").value("None")).optional()
                        )
                ));
    }

    @Test
    void deletePost() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/posts/{postId}", 1)
                        .cookie(new Cookie("SESSION", "AUTH_SESSION"))
                        .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "post/delete-post",
                        requestCookies(
                                CookieDocumentation.cookieWithName("SESSION").description("세션 ID")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                ));
    }

    @Test
    void getPost() throws Exception {
        given(postService.getPostDetail(anyLong()))
                .willReturn(PostDetail.builder()
                        .postId(1L)
                        .writerId(1L)
                        .writerNickname("작성자 닉네임")
                        .title("게시글 제목")
                        .content("게시글 내용")
                        .createdAt(LocalDateTime.now())
                        .mediaUrls(List.of("media_url_1", "media_url_2"))
                        .build()
                );

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/posts/{postId}", 1)
                                .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                        "post/get-post",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("게시글 ID"),
                                fieldWithPath("writerId").description("작성자 ID"),
                                fieldWithPath("writerNickname").description("작성자 닉네임"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 본문"),
                                fieldWithPath("createdAt").description("게시글 작성일시"),
                                fieldWithPath("mediaUrls").description("게시글 본문 미디어 URL")
                        )
                ));
    }

    @Test
    void getPostPage() throws Exception {
        int totalCount = 12;
        int pageSize = 10;
        int pageNumber = 0;

        List<PostDetail> postDetails = new ArrayList<>();
        for (int i=totalCount; i>= 0; i--) {
            postDetails.add(PostDetail.builder()
                    .postId(i)
                    .writerId(i)
                    .writerNickname("writer" + i)
                    .title("title" + i)
                    .content("게시글 내용")
                    .createdAt(LocalDateTime.now())
                    .mediaUrls(List.of("media_url_1", "media_url_2"))
                    .build());
        }
        Page<PostDetail> postDetailPage = new PageImpl<>(postDetails.subList(pageNumber * pageSize, pageSize), PageRequest.of(pageNumber, pageSize), totalCount);

        given(postService.getPostDetailPage(any(), any()))
                .willReturn(postDetailPage);

        mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/posts")
                                .queryParam("title", "title")
                                .queryParam("writerNickname", "writer")
                                .queryParam("size", "10")
                                .contentType(MediaType.ALL)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(
                                "post/get-post-page",
                                queryParameters(
                                        parameterWithName("title").description("게시글 제목").optional(),
                                        parameterWithName("writerNickname").description("게시글 작성자").optional(),
                                        parameterWithName("size").description("페이지 당 게시글 수").optional()
                                ),
                                responseFields(
                                        fieldWithPath("content").description("게시글 목록"),
                                        fieldWithPath("content[].postId").description("게시글 ID"),
                                        fieldWithPath("content[].title").description("게시글 제목"),
                                        fieldWithPath("content[].content").description("게시글 내용"),
                                        fieldWithPath("content[].writerId").description("작성자 ID"),
                                        fieldWithPath("content[].writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("content[].createdAt").description("작성일시"),
                                        fieldWithPath("content[].mediaUrls").description("게시글 첨부 미디어 URL 리스트"),

                                        fieldWithPath("pageable").description("페이징 관련 정보"),
                                        fieldWithPath("pageable.pageNumber").description("현재 페이지 번호"),
                                        fieldWithPath("pageable.pageSize").description("페이지당 아이템 개수"),
                                        fieldWithPath("pageable.sort.empty").description("정렬 기준이 비어있는지 여부"),
                                        fieldWithPath("pageable.sort.sorted").description("정렬 여부"),
                                        fieldWithPath("pageable.sort.unsorted").description("정렬되지 않았는지 여부"),
                                        fieldWithPath("pageable.offset").description("현재 페이지의 시작점 오프셋"),
                                        fieldWithPath("pageable.paged").description("페이징 여부"),
                                        fieldWithPath("pageable.unpaged").description("페이징이 아닌지 여부"),

                                        fieldWithPath("totalElements").description("총 요소 개수"),
                                        fieldWithPath("totalPages").description("총 페이지 수"),
                                        fieldWithPath("last").description("마지막 페이지 여부"),
                                        fieldWithPath("size").description("페이지 크기"),
                                        fieldWithPath("number").description("현재 페이지 번호"),
                                        fieldWithPath("sort.empty").description("정렬 기준이 비어있는지 여부"),
                                        fieldWithPath("sort.sorted").description("정렬 여부"),
                                        fieldWithPath("sort.unsorted").description("정렬되지 않았는지 여부"),
                                        fieldWithPath("numberOfElements").description("현재 페이지의 요소 개수"),
                                        fieldWithPath("first").description("첫 번째 페이지 여부"),
                                        fieldWithPath("empty").description("요소가 비어있는지 여부")
                                )
                        )
                );
    }
}