package com.editorial.platform.article.api.exception;

import com.editorial.platform.article.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import java.time.OffsetDateTime;

@Provider
public class ArticleNotFoundExceptionMapper
        implements ExceptionMapper<ArticleNotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ArticleNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                "Not Found",
                exception.getMessage(),
                uriInfo.getPath(),
                OffsetDateTime.now()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}