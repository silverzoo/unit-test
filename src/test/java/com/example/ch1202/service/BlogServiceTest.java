package com.example.ch1202.service;

import com.example.ch1202.domain.Article;
import com.example.ch1202.dto.AddArticleRequest;
import com.example.ch1202.dto.UpdateArticleRequest;
import com.example.ch1202.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlogServiceTest {
    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("블로그 글 저장 테스트")
    void saveArticleTest() {

        //given
        AddArticleRequest request = new AddArticleRequest("Test Title", "Test Content");
        Article article = request.toEntity();

        // Mockito 의 when() 사용
        // blogRepository.save() 메서드가 호출될 때 any(Article.class)를 인자로 사용하여 어떤 Article 객체가 들어와도
        // 위에서 생성한 article 을 반환하도록 설정
        when(blogRepository.save(any(Article.class))).thenReturn(article);

        //when
        Article savedArticle = blogService.save(request);

        //then
        assertNotNull(savedArticle);
        assertEquals("Test Title", savedArticle.getTitle());
        assertEquals("Test Content", savedArticle.getContent());
        verify(blogRepository, times(1)).save(any(Article.class));
    }

    @Test
    @DisplayName("블로그 전체 글 조회 테스트")
    void findAllArticlesTest() {

        //given
        List<Article> articles = Arrays.asList(
                new Article("Title 1", "Content 1"),
                new Article("Title 2", "Content 2")
        );

        // Mockito의 when() 사용
        // blogRepository.findAll() 메서드가 호출될 때 위에서 생성한 리스트를 반환하도록 설정
        when(blogRepository.findAll()).thenReturn(articles);

        //when
        List<Article> foundArticles = blogService.findAll();

        //then
        assertNotNull(foundArticles);
        assertEquals(2, foundArticles.size());
        assertEquals("Title 1", foundArticles.get(0).getTitle());
        assertEquals("Title 2", foundArticles.get(1).getTitle());
        verify(blogRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("ID로 블로그 글 조회 테스트")
    void findArticleByIdTest() {

        //given
        Long articleId = 1L;
        Article article = new Article("Test Title", "Test Content");

        when(blogRepository.findById(articleId)).thenReturn(Optional.of(article));

        //when
        Article foundArticle = blogService.findById(articleId);

        //then
        assertNotNull(foundArticle);
        assertEquals("Test Title", foundArticle.getTitle());
        assertEquals("Test Content", foundArticle.getContent());
        verify(blogRepository, times(1)).findById(articleId);

    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 테스트")
    void findArticleByNotExistedIdTest() {

        //given
        Long notExistedId = 999L;

        when(blogRepository.findById(notExistedId)).thenReturn(Optional.empty());

        //when & then
        //방법1.
//        assertThatThrownBy(() -> blogService.findById(notExistedId))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("not found : " + notExistedId);

        //방법2.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            blogService.findById(notExistedId);
        });
        assertTrue(exception.getMessage().contains("not found : " + notExistedId));


        verify(blogRepository, times(1)).findById(notExistedId);

    }

    @Test
    @DisplayName("블로그 글 삭제 테스트")
    void deleteArticleTest() {

        //given
        Long articleId = 1L;

        //when
        blogService.delete(articleId);

        //then
        verify(blogRepository, times(1)).deleteById(articleId);
    }

    @Test
    @DisplayName("블로그 글 업데이트 테스트")
    void updateArticleTest() {

        //given
        Long articleId = 1L;
        Article existingArticle = new Article("Old Title", "Old Content");
        UpdateArticleRequest updateRequest = new UpdateArticleRequest("New Title", "New Content");

        when(blogRepository.findById(articleId)).thenReturn(Optional.of(existingArticle));

        //when
        Article updatedArticle = blogService.update(articleId, updateRequest);

        //then
        assertNotNull(updatedArticle);
        assertEquals("New Title", updatedArticle.getTitle());
        assertEquals("New Content", updatedArticle.getContent());
        verify(blogRepository, times(1)).findById(articleId);
    }
}