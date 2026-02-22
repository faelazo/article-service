package com.editorial.platform.article.dto;

public class ArticleResponse {

    public Long id;
    public String title;
    public String author;

    public ArticleResponse(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}

