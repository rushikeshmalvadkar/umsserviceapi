package com.rm.ums.url.model.response;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateUrlResponse {
    private Long id;
    private String title;
    private String originalUrl;
    private String slug;
    private Long urlStatusId;
    private Long createdByUserId;
    private Instant createdOn;
}
