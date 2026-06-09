package com.rm.ums.url.model.request;

import com.rm.ums.common.model.response.dto.HeaderConfigAware;
import jakarta.validation.constraints.NotNull;

public record UpdateUrlRequest(
        @NotNull(message = "headerConfigId is required")
        Long headerConfigId,

        String value,

        @NotNull(message = "recordId is required")
        Long recordId) implements HeaderConfigAware {
}
