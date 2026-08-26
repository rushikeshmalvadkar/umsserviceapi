package com.rm.ums.url.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum VisitUrlStatusEnum {
    VALID_SLUG(""),
    UNKNOWN_SLUG("The requested short URL does not exist."),
    INACTIVE_SLUG("This short URL is currently inactive."),
    SHORT_URL_EXPIRED("This short URL is has been expired."),
    SHORT_URL_NOT_AVAILABLE_YET("This short URL is not available yet.");

    private final String message;

    public String message() {
        return message;
    }

}
