package com.editorial.platform.article.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(description = "Generic paginated response")
public class PagedResponse<T> {

    @Schema(description = "List of elements for current page")
    public List<T> content;

    @Schema(description = "Current page index (starting from 0)")
    public int page;

    @Schema(description = "Page size")
    public int size;

    @Schema(description = "Total number of elements")
    public long totalElements;

    @Schema(description = "Total number of pages")
    public int totalPages;

    public PagedResponse(List<T> content, int page, int size,
                         long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}