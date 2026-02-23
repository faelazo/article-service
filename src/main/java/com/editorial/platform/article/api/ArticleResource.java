package com.editorial.platform.article.api;

import com.editorial.platform.article.dto.ArticleResponse;
import com.editorial.platform.article.dto.ErrorResponse;
import com.editorial.platform.article.dto.PagedResponse;
import com.editorial.platform.article.service.ArticleService;
import com.editorial.platform.article.dto.ArticleRequest;
import jakarta.validation.Valid;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Articles", description = "Operations related to article management")
public class ArticleResource {

    @Inject
    ArticleService service;

    @GET
    @Operation(
            summary = "Get paginated list of articles",
            description = "Returns a paginated list of articles"
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Articles retrieved successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PagedResponse.class)
            )),
    })
    @APIResponse(
            responseCode = "400",
            description = "Invalid query parameters",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public PagedResponse<ArticleResponse> getArticles(@QueryParam("page")
                                                          @DefaultValue("0")
                                                          @Parameter(description = "Page number (starting from 0)", example = "0")
                                                          @Min(value = 0, message = "page must be >= 0") int page,
                                                      @QueryParam("size") @DefaultValue("10")
                                                      @Parameter(description = "Size number must be between 1 and 100", example = "10")
                                                      @Min(value = 1, message = "size must be >= 1")
                                                      @Max(value = 100, message = "size must be <= 100") int size,
                                                      @QueryParam("sortBy")
                                                          @Pattern(regexp = "id|title|author", message = "Invalid sort field")
                                                          @DefaultValue("id")
                                                          String sortBy,
                                                      @QueryParam("direction")
                                                          @Pattern(regexp = "asc|desc", message = "direction must be asc or desc")
                                                          @DefaultValue("asc")
                                                          String direction,
                                                      @QueryParam("title")
                                                          String title,
                                                      @QueryParam("author")
                                                          String author
                                                      ) {
        return service.listAll(page, size, sortBy, direction, title, author);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get article by ID")
    @APIResponse(
            responseCode = "200",
            description = "Article found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArticleResponse.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Article not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public ArticleResponse getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Operation(
            summary = "Create a new article",
            description = "Creates an article and returns the created resource"
    )
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Article created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArticleResponse.class)
            )),
            @APIResponse(responseCode = "400", description = "Validation error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )), })
    @RequestBody(
            content = @Content(
                    examples = @ExampleObject(
                            name = "Sample Article",
                            value = """
                    {
                      "title": "Understanding Kubernetes",
                      "author": "Platform Team"
                    }
                    """
                    )
            )
    )
    public Response create(@Valid ArticleRequest request) {
        ArticleResponse created = service.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update existing article")
    @APIResponse(
            responseCode = "200",
            description = "Article updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArticleResponse.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Article not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public ArticleResponse update(@PathParam("id") Long id,
                           @Valid ArticleRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete article")
    @APIResponse(responseCode = "204", description = "Article deleted")
    @APIResponse(
            responseCode = "404",
            description = "Article not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public Response delete(@PathParam("id") Long id) {

        service.delete(id);

        return Response.noContent().build();
    }
}