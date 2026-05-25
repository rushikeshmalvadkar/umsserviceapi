package com.rm.ums.url.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UrlStatusEnum {
    ACTIVE(1L),
    IN_ACTIVE(2L);

    private final Long id;

    public Long id() {
        return id;
    }
}
