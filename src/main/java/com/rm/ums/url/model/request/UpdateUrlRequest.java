package com.rm.ums.url.model.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUrlRequest(
        @NotNull(message = "headerMappingId is required")
        Long headerMappingId,

        String value,

        @NotNull(message = "recordId is required")
        Long recordId) {
}
