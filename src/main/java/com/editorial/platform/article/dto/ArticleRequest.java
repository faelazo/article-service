package com.editorial.platform.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ArticleRequest {

	@NotBlank(message = "title is required")
	@Size(max = 200, message = "title must be at most 200 characters")
    public String title;

	@NotBlank(message = "author is required")
    @Size(max = 100, message = "author must be at most 100 characters")
    public String author;
}
