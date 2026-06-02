package com.rm.ums.url.model.response;

import lombok.Data;

import java.time.Instant;

@Data
public class FetchUrlsDataResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private String urlStatusName;
    private Instant createdDate;
}
