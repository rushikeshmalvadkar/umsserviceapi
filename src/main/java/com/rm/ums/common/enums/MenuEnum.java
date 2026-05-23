package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuEnum {
    CREATE_URL(2L);

    private final Long id;

    public Long id() {
        return id;
    }
}
