package com.editorial.platform.article.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Standard error response")
public class ErrorResponse {

    @Schema(description = "Timestamp of the error")
    public OffsetDateTime timestamp;
    @Schema(description = "HTTP status code")
    public int status;
    @Schema(description = "Error type")
    public String error;
    @Schema(description = "Error message")
    public String message;
    @Schema(description = "Request path")
    public String path;

    public ErrorResponse(int status, String error, String message, String path, OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}