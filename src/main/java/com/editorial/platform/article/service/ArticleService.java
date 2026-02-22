package com.editorial.platform.article.service;

import com.editorial.platform.article.api.exception.ArticleNotFoundException;
import com.editorial.platform.article.domain.Article;
import com.editorial.platform.article.dto.ArticleRequest;
import com.editorial.platform.article.dto.ArticleResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ArticleService {

    public List<ArticleResponse> listAll() {

        return Article.<Article>listAll().stream().map(this::toResponse).toList();
    }

    public ArticleResponse findById(Long id) {
        Article article = Article.findById(id);

        if (article == null) throw new ArticleNotFoundException(id);

        return toResponse(article);
    }

    @Transactional
    public ArticleResponse create(ArticleRequest request) {

        Article article = new Article();
        article.title = request.title;
        article.author = request.author;

        article.persist();
        return toResponse(article);
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleRequest request) {

        Article article = Article.findById(id);

        if (article == null) {
            throw new ArticleNotFoundException(id);
        }

        article.title = request.title;
        article.author = request.author;

        return toResponse(article); // Panache hace dirty checking automático
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = Article.deleteById(id);

        if (!deleted) throw new ArticleNotFoundException(id);

    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(
                article.id,
                article.title,
                article.author
        );
    }
}