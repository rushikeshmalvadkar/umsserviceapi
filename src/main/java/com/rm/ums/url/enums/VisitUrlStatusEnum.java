package com.rm.ums.url.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum VisitUrlStatusEnum {
    VALID_SLUG(""),
    UNKNOWN_SLUG("The requested short URL does not exist."),
    INACTIVE_SLUG("This short URL is currently inactive.");

    private final String message;

    public String message() {
        return message;
    }

}
