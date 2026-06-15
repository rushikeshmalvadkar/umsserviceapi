package com.rm.ums.common.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public enum UmsResponseStatusEnum {
    SUCCESS(OK.value()),
    CREATED(HttpStatus.CREATED.value()),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value()),
    PERMISSION_DENIED(FORBIDDEN.value());

    private final int code;

    public int code() {
        return code;
    }
}
