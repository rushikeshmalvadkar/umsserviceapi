package com.rm.ums.url.model.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUrlRequest(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Original url is required")
        String originalUrl,
        String slug) {
}
