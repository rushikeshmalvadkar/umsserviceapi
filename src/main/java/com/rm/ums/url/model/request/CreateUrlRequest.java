package com.rm.ums.url.model.request;

import jakarta.validation.constraints.NotBlank;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;


public record CreateUrlRequest(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "original url is required")
        String originalUrl,
        String slug) {

    public boolean hasSlug() {
        return isNoneBlank(slug);
    }
}
