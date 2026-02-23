package com.editorial.platform.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Article creation request")
public class ArticleRequest {

    @Schema(description = "Article title")
	@NotBlank(message = "title is required")
	@Size(max = 200, message = "title must be at most 200 characters")
    public String title;

    @Schema(description = "Author name")
	@NotBlank(message = "author is required")
    @Size(max = 100, message = "author must be at most 100 characters")
    public String author;
}
