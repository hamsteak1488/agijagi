package com.password926.agijagi.post.controller;

import com.password926.agijagi.config.RestDocsConfig;
import com.password926.agijagi.config.TestAuthConfig;
import com.password926.agijagi.post.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import({RestDocsConfig.class, TestAuthConfig.class})
@AutoConfigureRestDocs
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void createComment() throws Exception {
//        mockMvc.perform(
//
//        )
    }

    @Test
    void updatePostComment() {
    }

    @Test
    void deletePostComment() {
    }

    @Test
    void getPostComments() {
    }
}