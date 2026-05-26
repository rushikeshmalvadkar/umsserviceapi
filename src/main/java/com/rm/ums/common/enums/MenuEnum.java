package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuEnum {
    CREATE_URL(2L),
    MY_SHORT_URLS(3L);

    private final Long id;

    public Long id() {
        return id;
    }
}
