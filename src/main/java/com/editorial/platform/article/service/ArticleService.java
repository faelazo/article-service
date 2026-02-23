package com.editorial.platform.article.service;

import com.editorial.platform.article.api.exception.ArticleNotFoundException;
import com.editorial.platform.article.domain.Article;
import com.editorial.platform.article.dto.ArticleRequest;
import com.editorial.platform.article.dto.ArticleResponse;
import com.editorial.platform.article.dto.PagedResponse;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Page;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ArticleService {

    public PagedResponse<ArticleResponse> listAll(
            int page,
            int size,
            String sortBy,
            String direction,
            String title,
            String author
            ) {

        Sort sort = direction.equalsIgnoreCase("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        StringBuilder queryBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            queryBuilder.append("lower(title) like ?1");
            params.add("%" + title.toLowerCase() + "%");
        }

        if (author != null && !author.isBlank()) {
            if (!queryBuilder.isEmpty()) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("lower(author) like ?").append(params.size() + 1);
            params.add("%" + author.toLowerCase() + "%");
        }

        PanacheQuery<Article> query;

        if (queryBuilder.isEmpty()) {
            query = Article.findAll(sort);
        } else {
            query = Article.find(queryBuilder.toString(), sort, params.toArray());
        }


        long totalElements = query.count();

        List<ArticleResponse> content = query.page(Page.of(page, size)).list().stream().map(this::toResponse).toList();

        int totalPages = (int) Math.ceil((double) totalElements/size);

        return new PagedResponse<>(content, page, size, totalElements, totalPages);
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