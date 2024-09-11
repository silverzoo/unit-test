package com.example.unittest.controller;

import com.example.unittest.domain.Article;
import com.example.unittest.dto.AddArticleRequest;
import com.example.unittest.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    //직렬화 역직렬화 인터페이스 필요
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        //샘플데이터 삭제해주어야... Truncate
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("addArticle: 블로그 글 추가")
    void addArticle() throws Exception {

        // given
        // url 필요
        final String url = "/api/articles";

        // 블로그 글, 내용
        final String title = "title1";
        final String content = "content1";

        // request 요청할 dto 생성
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // dto -> json 변환 후 request (메시지 바디에 담아야된다)
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 데이터를 요청하고, 응답을 받는다
        ResultActions result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        // 생성 완료 응답 코드 확인
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        // response 응답 메시지바디의 내용과 예상값과 비교 및 겸증
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }
}