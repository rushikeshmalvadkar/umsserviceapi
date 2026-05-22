package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UmsResponseMessageEnum {

    SIGNED_IN_SUCCESSFULLY("Signed in successfully");

    private final String value;

    public String value() {
        return value;
    }
}
