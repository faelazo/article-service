package com.editorial.platform.article.api.exception;

public class ArticleNotFoundException extends RuntimeException{

    public ArticleNotFoundException(Long id){
        super("Article with id " + id + " not found");
    }
}
