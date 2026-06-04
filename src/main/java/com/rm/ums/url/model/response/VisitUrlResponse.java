package com.rm.ums.url.model.response;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.enums.VisitUrlStatusEnum;

import static com.rm.ums.url.enums.VisitUrlStatusEnum.*;


public record VisitUrlResponse(String originalUrl, VisitUrlStatusEnum urlStatusEnum) {

    public static VisitUrlResponse withValidSlugStatus(UrlEntity entity) {
        return new VisitUrlResponse(entity.getOriginalUrl(), VALID_SLUG);
    }

    public static VisitUrlResponse withUnknownSlugStatus() {
        return new VisitUrlResponse(null, UNKNOWN_SLUG);
    }

    public static VisitUrlResponse withInactiveSlugStatus() {
        return new VisitUrlResponse(null, INACTIVE_SLUG);
    }
}