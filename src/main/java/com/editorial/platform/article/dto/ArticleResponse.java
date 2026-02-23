package com.editorial.platform.article.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Article response payload")
public class ArticleResponse {

    @Schema(description = "Article identifier")
    public Long id;
    @Schema(description = "Article's title")
    public String title;
    @Schema(description = "Article's author")
    public String author;

    public ArticleResponse(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}

