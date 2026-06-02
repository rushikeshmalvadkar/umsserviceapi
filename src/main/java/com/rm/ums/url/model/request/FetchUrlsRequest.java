package com.rm.ums.url.model.request;

import static java.util.Objects.nonNull;

public record FetchUrlsRequest(Long urlStatusId) {
    public boolean hasUrlStatusId() {
        return nonNull(urlStatusId);
    }
}
