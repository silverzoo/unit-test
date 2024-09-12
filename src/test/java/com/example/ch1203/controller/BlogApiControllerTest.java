package com.example.ch1203.controller;

import com.example.ch1203.domain.Article;
import com.example.ch1203.dto.UpdateArticleRequest;
import com.example.ch1203.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("findAllArticles: 블로그 글 목록 조회 성공 테스트")
    public void findAllArticles() throws Exception {

        //given
        final String url = "/api/articles";
        final String title = "title", content = "content";

        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();

        blogRepository.save(article);

        //when
        ResultActions result = mockMvc.perform(
                get(url)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));

    }

    @Test
    @DisplayName("findArticle: 블로그 글 조회 성공 테스트")
    public void findArticle() throws Exception {

        // given
        final String url = "/api/articles/{id}";
        final String title = "title", content = "content";

        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();

        Article savedArticle = blogRepository.save(article);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url, savedArticle.getId())
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));

    }

    @Test
    @DisplayName("updateArticle: 블로그 글 수정 성공 테스트")
    public void updateArticle() throws Exception {

        // given
        final String url = "/api/articles/{id}";
        final String title = "title", content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

    }


    @Test
    @DisplayName("deleteArticle: 블로그 글 삭제 성공 테스트")
    public void deleteArticle() throws Exception {

        // given
        final String url = "/api/articles/{id}";
        final String title = "title", content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();

    }

}


