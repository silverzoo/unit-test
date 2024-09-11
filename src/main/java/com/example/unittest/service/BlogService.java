package com.example.unittest.service;

import com.example.unittest.domain.Article;
import com.example.unittest.dto.AddArticleRequest;
import com.example.unittest.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
