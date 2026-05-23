package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UmsResponseMessageEnum {

    SIGNED_IN_SUCCESSFULLY("Signed in successfully"),
    FETCHED_SUCCESSFULLY("Fetched successfully");

    private final String value;

    public String value() {
        return value;
    }
}
