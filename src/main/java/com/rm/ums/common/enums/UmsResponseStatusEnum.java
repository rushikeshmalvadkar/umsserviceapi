package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public enum UmsResponseStatusEnum {
    SUCCESS(OK.value()),
    CREATED(HttpStatus.CREATED.value());

    private final int code;

    public int code() {
        return code;
    }
}
