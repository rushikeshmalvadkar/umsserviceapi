package com.rm.ums.url.model.response;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class FetchUrlsDataResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private String urlStatusName;
    private Long viewCount;
    private Instant createdDate;
    private LocalDate startAt;
    private LocalDate expireAt;
}
