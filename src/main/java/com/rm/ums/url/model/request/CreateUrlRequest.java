package com.rm.ums.url.model.request;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;


public record CreateUrlRequest(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "originalUrl is required")
        String originalUrl,
        String slug,
        Instant startAt,
        Instant expireAt) {

    public boolean hasSlug() {
        return isNoneBlank(slug);
    }

    public boolean hasNoExpirationTime() {
        return startAt ==null && expireAt ==null;
    }
}
