package com.editorial.platform.article.api;

import com.editorial.platform.article.domain.Article;
import com.editorial.platform.article.dto.ArticleResponse;
import com.editorial.platform.article.service.ArticleService;
import com.editorial.platform.article.dto.ArticleRequest;
import jakarta.validation.Valid;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService service;

    @GET
    public List<ArticleResponse> getArticles() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public ArticleResponse getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(@Valid ArticleRequest request) {
        ArticleResponse created = service.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public ArticleResponse update(@PathParam("id") Long id,
                           @Valid ArticleRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        service.delete(id);

        return Response.noContent().build();
    }
}