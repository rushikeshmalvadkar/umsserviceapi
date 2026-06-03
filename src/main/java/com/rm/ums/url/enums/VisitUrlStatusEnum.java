package com.rm.ums.url.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum VisitUrlStatusEnum {
    FOUND(null),
    NOT_FOUND("URL not found"),
    EXPIRED("URL has expired");

    private final String message;

}
