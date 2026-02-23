package com.editorial.platform.article.dto;

import java.util.List;

public class PagedResponse<T> {

    public List<T> content;
    public int page;
    public int size;
    public long totalElements;
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