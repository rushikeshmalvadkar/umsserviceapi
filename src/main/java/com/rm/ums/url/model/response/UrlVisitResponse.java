package com.rm.ums.url.model.response;

import com.rm.ums.url.enums.VisitUrlStatusEnum;
import lombok.Data;

@Data
public class UrlVisitResponse {

    private String originalUrl;
    private VisitUrlStatusEnum visitStatus;

    private UrlVisitResponse(VisitUrlStatusEnum visitStatus, String originalUrl) {
        this.originalUrl = originalUrl;
        this.visitStatus = visitStatus;
    }

    public static UrlVisitResponse success(String originalUrl) {
        return new UrlVisitResponse(VisitUrlStatusEnum.FOUND, originalUrl);
    }

    public static UrlVisitResponse notFound() {
        return new UrlVisitResponse(VisitUrlStatusEnum.NOT_FOUND, null);
    }

    public static UrlVisitResponse expired() {
        return new UrlVisitResponse(VisitUrlStatusEnum.EXPIRED, null);
    }
}
