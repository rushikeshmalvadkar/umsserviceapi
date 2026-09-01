package com.rm.ums.url.model.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;


public record CreateUrlRequest(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "originalUrl is required")
        String originalUrl,
        String slug,
        LocalDate startAt,
        LocalDate expireAt) {

    public boolean hasSlug() {
        return isNoneBlank(slug);
    }

    public boolean userIsCreatingExpiryBasedUrl() {
        return nonNull(startAt) && nonNull(expireAt);
    }
}
